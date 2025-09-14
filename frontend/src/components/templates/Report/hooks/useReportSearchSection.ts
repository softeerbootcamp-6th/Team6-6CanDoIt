import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import useApiQuery from '../../../../hooks/useApiQuery.ts';
import type {
    MountainCourse,
    MountainData,
} from '../../../../types/mountainTypes';
import type { Option } from '../../../../types/searchBarTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../../Main/utils.ts';

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

    const { data: mountainsData, isError: isMountainsError } = useApiQuery<
        MountainData[]
    >(
        '/card/mountain',
        {},
        {
            retry: false,
            networkMode: 'always',
            staleTime: 5 * 60 * 1000,
            gcTime: 24 * 60 * 60 * 1000,
            placeholderData: (prev) => prev,
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
            staleTime: 24 * 60 * 60 * 1000,
            gcTime: 24 * 60 * 60 * 1000,
        },
    );
    const { data: filterColumn, isError: isFilterColumnError } = useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            placeholderData: (prev) => prev,
            retry: false,
            networkMode: 'always',
            staleTime: 24 * 60 * 60 * 1000,
            gcTime: 24 * 60 * 60 * 1000,
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
