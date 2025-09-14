import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import type { Option } from '../../../../types/searchBarTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../../../../utils/utils.ts';
import useMountainsData from '../../../../hooks/useMountainsData.ts';
import useCoursesData from '../../../../hooks/useCoursesData.ts';
import useFilterColumn from '../../../../hooks/useFilterColumn.ts';

export default function useReportSearchSection() {
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

    const { data: mountainsData, isError: isMountainsError } =
        useMountainsData();
    const { data: coursesData, isError: isCoursesError } =
        useCoursesData(selectedMountainId);
    const { data: filterColumn, isError: isFilterColumnError } =
        useFilterColumn();

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

    return {
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
    };
}
