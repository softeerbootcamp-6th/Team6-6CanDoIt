import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Option } from '../../../../types/searchBarTypes';
import useMountainsData from '../../../../hooks/useMountainsData.ts';
import useCoursesData from '../../../../hooks/useCoursesData.ts';
import { validate } from '../helper.ts';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../../../../utils/utils.ts';

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

    const { data: mountainsData, isError: isMountainsError } =
        useMountainsData();
    const { data: coursesData, isError: isCoursesError } =
        useCoursesData(selectedMountainId);

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
