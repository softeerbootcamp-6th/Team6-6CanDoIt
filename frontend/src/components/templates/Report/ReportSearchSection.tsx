import SearchBar from '../../organisms/Common/SearchBar.tsx';
import { css } from '@emotion/react';
import { useState } from 'react';
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

interface Option {
    id: number;
    name: string;
}

export default function ReportSearchSection() {
    const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
    const [filterAnchor, setFilterAnchor] = useState<HTMLElement | null>(null);
    const [searchParams, setSearchParams] = useSearchParams();
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));

    const [selectedMountainId, setSelectedMountainId] =
        useState<number>(mountainId);
    const [selectedCourseId, setSelectedCourseId] = useState<number>(courseId);
    const mouuntainChangeHandler = (id: number) => {
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

    const { data: mountainsData } = useApiQuery<MountainData[]>(
        '/card/mountain',
        {},
        {
            //placeholderData: MountainsData,
            retry: false,
        },
    );
    const { data: coursesData } = useApiQuery<MountainCourse[]>(
        `/card/mountain/${selectedMountainId}/course`,
        {},
        {
            //placeholderData: initCoursesData,
            enabled: selectedMountainId !== 0,
            retry: false,
        },
    );
    const { data: filterColumn } = useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            placeholderData: filterKeywords,
            retry: false,
        },
    );

    const mountainOptions: Option[] = refactorMountainDataToOptions(
        mountainsData ?? [],
    );
    const courseOptions: Option[] = refactorCoursesDataToOptions(
        coursesData ?? [],
    );

    const filterClickHandler = (e: React.MouseEvent<HTMLElement>) => {
        setFilterAnchor(e.currentTarget);
        setIsFilterModalOpen(true);
    };

    return (
        <div css={searchWrapperStyle}>
            {mountainId && courseId && mountainId !== 0 && courseId !== 0 && (
                <ChipButton hidden text='필터' iconName='filter-lines' />
            )}

            <div css={searchBarStyle}>
                <SearchBar
                    searchBarTitle='어디 날씨를 확인해볼까요?'
                    searchBarMessage='의 실시간 제보'
                    pageName='report'
                    mountainOptions={mountainOptions}
                    selectedMountainId={selectedMountainId ?? 0}
                    mountainChangeHandler={mouuntainChangeHandler}
                    courseOptions={courseOptions}
                    selectedCourseId={selectedCourseId ?? 0}
                    courseChangeHandler={courseChangeHandler}
                    onSubmit={submitHandler}
                />
            </div>

            {mountainId && courseId && mountainId !== 0 && courseId !== 0 && (
                <>
                    <ChipButton
                        onClick={filterClickHandler}
                        text='필터'
                        iconName='filter-lines'
                    />
                    <FilterModal
                        isOpen={isFilterModalOpen}
                        onClose={() => setIsFilterModalOpen(false)}
                        title='필터'
                        description='원하는 날씨 조건을 선택하세요'
                        filterColumn={filterColumn ?? filterKeywords}
                        anchorElement={filterAnchor}
                    />
                </>
            )}
        </div>
    );
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
