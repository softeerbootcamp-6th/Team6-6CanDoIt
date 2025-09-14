import SearchBar from '../../organisms/Common/SearchBar.tsx';
import { css } from '@emotion/react';
import FilterModal from '../../organisms/Report/FilterModal.tsx';
import ChipButton from '../../molecules/Button/ChipButton.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import type { Option } from '../../../types/searchBarTypes';
import useReportSearchSection from './hooks/useReportSearchSection.ts';
import type { FilterColumn } from '../../../types/filterTypes';

type SearchBarProps = Parameters<typeof SearchBar>[0];
type ChipButtonProps = Parameters<typeof ChipButton>[0];
type FilterModalProps = Parameters<typeof FilterModal>[0];

export default function ReportSearchSection() {
    const {
        mountainId,
        courseId,
        selectedMountainId,
        selectedCourseId,
        mountainOptions,
        courseOptions,
        isFilterModalOpen,
        filterColumn,
        filterAnchor,
        errorMessage,
        setErrorMessage,

        mountainChangeHandler,
        courseChangeHandler,
        submitHandler,
        filterClickHandler,
        setIsFilterModalOpen,
    } = useReportSearchSection();

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
        filterColumn ?? [],
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
    filterColumn: FilterColumn,
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
