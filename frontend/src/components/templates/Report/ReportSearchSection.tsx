import SearchBar from '../../organisms/Common/SearchBar.tsx';
import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../Main/utils.ts';
import { useSearchParams } from 'react-router-dom';
import FilterModal from '../../organisms/Report/FilterModal.tsx';
import ChipButton from '../../molecules/Button/ChipButton.tsx';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import type { Option } from '../../../types/searchBarTypes';

type SearchBarProps = Parameters<typeof SearchBar>[0];
type ChipButtonProps = Parameters<typeof ChipButton>[0];
type FilterModalProps = Parameters<typeof FilterModal>[0];

export default function ReportSearchSection() {
    const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
    const [filterAnchor, setFilterAnchor] = useState<HTMLElement | null>(null);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [searchParams, setSearchParams] = useSearchParams();
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));
    const [selectedMountainId, setSelectedMountainId] =
        useState<number>(mountainId);
    const [selectedCourseId, setSelectedCourseId] = useState<number>(courseId);

    const mountainChangeHandler = (id: number) => {
        setSelectedMountainId(id);
        setSelectedCourseId(0);
    };
    const courseChangeHandler = (id: number) => {
        setSelectedCourseId(id);
    };
    const submitHandler = (e: React.FormEvent) => {
        e.preventDefault();

        const next = new URLSearchParams(searchParams);
        next.set('mountainid', String(selectedMountainId));
        next.set('courseid', String(selectedCourseId));
        setSearchParams(next);
    };
    const filterClickHandler = (e: React.MouseEvent<HTMLElement>) => {
        setFilterAnchor(e.currentTarget);
        setIsFilterModalOpen(true);
    };

    const { data: mountainsData, isError: isMountainsError } = useApiQuery<
        MountainData[]
    >(
        '/card/mountain',
        {},
        {
            retry: false,
            networkMode: 'always',
            staleTime: 5 * 60 * 1000,
            gcTime: 1000 * 60 * 1000,
        },
    );
    const { data: coursesData, isError: isCoursesError } = useApiQuery<
        MountainCourse[]
    >(
        `/card/mountain/${selectedMountainId}/course`,
        {},
        {
            enabled: selectedMountainId !== 0,
            retry: false,
            networkMode: 'always',
            staleTime: 1000 * 60 * 1000,
            gcTime: 1000 * 60 * 1000,
        },
    );
    const { data: filterColumn, isError: isFilterColumnError } = useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            placeholderData: filterKeywords,
            retry: false,
            networkMode: 'always',
            staleTime: 1000 * 60 * 1000,
            gcTime: 1000 * 60 * 1000,
        },
    );
    useEffect(() => {
        const errorMessage = isMountainsError
            ? '산 정보를 불러오지 못했습니다.'
            : isCoursesError
              ? '코스 정보를 불러오지 못했습니다.'
              : isFilterColumnError
                ? '필터 정보를 불러오지 못했습니다.'
                : '';
        setErrorMessage(errorMessage);
    }, [isMountainsError, isCoursesError, isFilterColumnError]);

    const mountainOptions: Option[] = refactorMountainDataToOptions(
        mountainsData ?? [],
    );
    const courseOptions: Option[] = refactorCoursesDataToOptions(
        coursesData ?? [],
    );

    const searchBarProps = getSearchBarProps(
        mountainOptions,
        selectedMountainId ?? 0,
        mountainChangeHandler,
        courseOptions,
        selectedCourseId ?? 0,
        courseChangeHandler,
        submitHandler,
    );
    const chipButtonProps = getChipButtonProps(true, filterClickHandler);
    const filterModalProps = getFilterModalProps(
        isFilterModalOpen,
        () => setIsFilterModalOpen(false),
        filterColumn ?? filterKeywords,
        filterAnchor,
    );

    return (
        <div css={searchWrapperStyle}>
            {mountainId && courseId && mountainId !== 0 && courseId !== 0 && (
                <ChipButton hidden text='필터' iconName='filter-lines' />
            )}

            <div css={searchBarStyle}>
                <SearchBar {...searchBarProps} />
            </div>
            {errorMessage && (
                <Modal
                    onClose={() => {
                        setErrorMessage('');
                    }}
                >
                    {errorMessage}
                </Modal>
            )}
            {mountainId && courseId && mountainId !== 0 && courseId !== 0 && (
                <>
                    <ChipButton {...chipButtonProps} />
                    <FilterModal {...filterModalProps} />
                </>
            )}
        </div>
    );
}

function getSearchBarProps(
    mountainOptions: Option[],
    selectedMountainId: number,
    mouuntainChangeHandler: (id: number) => void,
    courseOptions: Option[],
    selectedCourseId: number,
    courseChangeHandler: (id: number) => void,
    submitHandler: (e: React.FormEvent) => void,
): SearchBarProps {
    return {
        searchBarTitle: '어디 날씨를 확인해볼까요?',
        searchBarMessage: '의 실시간 제보',
        pageName: 'report',
        mountainOptions,
        selectedMountainId,
        mountainChangeHandler: mouuntainChangeHandler,
        courseOptions,
        selectedCourseId,
        courseChangeHandler,
        onSubmit: submitHandler,
    };
}

function getChipButtonProps(
    show: boolean,
    clickHandler: (e: React.MouseEvent<HTMLElement>) => void,
): ChipButtonProps {
    return {
        hidden: !show,
        text: '필터',
        iconName: 'filter-lines',
        onClick: clickHandler,
    };
}

function getFilterModalProps(
    isOpen: boolean,
    onClose: () => void,
    filterColumn: typeof filterKeywords,
    anchorElement: HTMLElement | null,
): FilterModalProps {
    return {
        isOpen,
        onClose,
        title: '필터',
        description: '원하는 날씨 조건을 선택하세요',
        filterColumn,
        anchorElement,
    };
}

const searchWrapperStyle = css`
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding: 0 2rem;
    box-sizing: border-box;
`;

const searchBarStyle = css`
    width: 100%;
`;

const filterKeywords = {
    weatherKeywords: [
        { id: 0, description: '화창해요' },
        { id: 1, description: '구름이 많아요' },
        { id: 2, description: '더워요' },
        { id: 3, description: '추워요' },
    ],
    rainKeywords: [
        { id: 0, description: '부슬비가 내려요' },
        { id: 1, description: '장대비가 쏟아져요' },
        { id: 2, description: '천둥 번개가 쳐요' },
        { id: 3, description: '폭우가 내려요' },
    ],
    etceteraKeywords: [
        { id: 0, description: '안개가 껴요' },
        { id: 1, description: '미세먼지가 많아요' },
        { id: 2, description: '시야가 흐려요' },
    ],
};
