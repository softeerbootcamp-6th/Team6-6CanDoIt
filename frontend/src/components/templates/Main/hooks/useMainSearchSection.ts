import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
    validate,
} from '../utils.ts';
import useApiQuery from '../../../../hooks/useApiQuery.ts';
import type {
    MountainCourse,
    MountainData,
} from '../../../../types/mountainTypes';
import type { Option } from '../../../../types/searchBarTypes';

export default function useMainSearchSection() {
    const [selectedMountainId, setSelectedMountainId] = useState<number>(0);
    const [selectedCourseId, setSelectedCourseId] = useState<number>(0);
    const [selectedWeekdayId, setSelectedWeekdayId] = useState<number>(0);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const mountainChangeHandler = (id: number) => {
        setSelectedMountainId(id);
        setSelectedCourseId(0);
    };
    const courseChangeHandler = (id: number) => {
        setSelectedCourseId(id);
    };
    const weekdayChangeHandler = (id: number) => {
        setSelectedWeekdayId(id);
    };
    const navigate = useNavigate();

    const isFormValidate = (e: React.FormEvent) => {
        e.preventDefault();

        const error = validate({
            mountainId: selectedMountainId,
            courseId: selectedCourseId,
            weekdayId: selectedWeekdayId,
        });
        if (error) return false;
        return true;
    };

    const navigateToForecast = () => {
        navigate(
            `/forecast?mountainid=${selectedMountainId}&courseid=${selectedCourseId}&weekdayid=${selectedWeekdayId}`,
        );
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

    useEffect(() => {
        if (isMountainsError || isCoursesError) {
            setIsModalOpen(true);
        }
    }, [isMountainsError, isCoursesError]);

    const mountainOptions: Option[] = refactorMountainDataToOptions(
        mountainsData ?? [],
    );
    const courseOptions: Option[] = refactorCoursesDataToOptions(
        coursesData ?? [],
    );

    return {
        selectedMountainId,
        selectedCourseId,
        selectedWeekdayId,
        mountainOptions,
        courseOptions,
        mountainChangeHandler,
        courseChangeHandler,
        weekdayChangeHandler,
        isModalOpen,
        setIsModalOpen,
        isMountainsError,
        isCoursesError,
        isFormValidate,
        navigateToForecast,
    };
}
