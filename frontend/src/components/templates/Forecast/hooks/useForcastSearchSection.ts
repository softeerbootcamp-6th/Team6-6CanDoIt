import { useSearchParams } from 'react-router-dom';
import { useEffect, useMemo, useRef, useState } from 'react';
import useMountainsData from '../../../../hooks/useMountainsData.ts';
import useCoursesData from '../../../../hooks/useCoursesData.ts';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../../../../utils/utils.ts';
import type { Option } from '../../../../types/searchBarTypes';

export default function useForecastSearchSection() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));
    const weekdayId = Number(searchParams.get('weekdayid'));

    const [selectedMountainId, setSelectedMountainId] =
        useState<number>(mountainId);
    const [selectedCourseId, setSelectedCourseId] = useState<number>(courseId);
    const [selectedWeekdayId, setSelectedWeekdayId] =
        useState<number>(weekdayId);

    const searchedMountainIdsRef = useRef<Set<number>>(new Set());

    const { data: mountainsData, isError: isMountainsError } =
        useMountainsData();
    const { data: coursesData, isError: isCoursesError } =
        useCoursesData(selectedMountainId);
    useEffect(() => {
        const errorMessage = isMountainsError
            ? '산 정보를 불러오지 못했습니다.'
            : isCoursesError
              ? '코스 정보를 불러오지 못했습니다.'
              : '';
        setErrorMessage(errorMessage);
    }, [isMountainsError, isCoursesError]);

    const selectedMountain = useMemo(() => {
        return mountainsData?.find(
            (mountain) => mountain.mountainId === selectedMountainId,
        );
    }, [mountainsData, selectedMountainId]);
    const mountainTitle = selectedMountain?.mountainName ?? '';
    const mountainDescription = selectedMountain?.mountainDescription ?? '';

    const mouuntainChangeHandler = (id: number) => {
        setSelectedMountainId(id);
        setSelectedCourseId(0);
    };
    const courseChangeHandler = (id: number) => {
        setSelectedCourseId(id);
    };
    const weekdayChangeHandler = (id: number) => {
        setSelectedWeekdayId(id);
    };
    const submitHandler = (e: React.FormEvent) => {
        e.preventDefault();
        if (
            selectedMountainId === 0 ||
            selectedCourseId === 0 ||
            selectedWeekdayId === 0
        )
            return;

        const next = new URLSearchParams(searchParams);
        next.set('mountainid', String(selectedMountainId));
        next.set('courseid', String(selectedCourseId));
        next.set('weekdayid', String(selectedWeekdayId));
        setSearchParams(next);
    };

    const mountainOptions: Option[] = useMemo(
        () => refactorMountainDataToOptions(mountainsData ?? []),
        [mountainsData],
    );
    const courseOptions = useMemo(
        () => refactorCoursesDataToOptions(coursesData ?? []),
        [coursesData],
    );

    useEffect(() => {
        if (courseId !== 0 || !coursesData?.length) return;
        const newCourseId = selectedCourseId || coursesData[0].courseId;
        if (!newCourseId) return;

        setSelectedCourseId(newCourseId);
        setSearchParams((prev) => {
            const next = new URLSearchParams(prev);
            next.set('courseid', String(newCourseId));
            return next;
        });
    }, [courseId, coursesData, selectedCourseId]);
    useEffect(() => {
        if (weekdayId !== 0) return;
        const newWeekdayId = weekdayData[0].id;
        setSelectedWeekdayId(newWeekdayId);
        setSearchParams((prev) => {
            const next = new URLSearchParams(prev);
            next.set('weekdayid', String(newWeekdayId));
            return next;
        });
    }, [weekdayId, courseId]);
    useEffect(() => {
        if (mountainId === 0) return;
        if (searchedMountainIdsRef.current.has(mountainId)) {
            setIsLoading(false);
            return;
        }

        setIsLoading(true);
        const timer = setTimeout(() => {
            setIsLoading(false);
            searchedMountainIdsRef.current.add(mountainId);
        }, 3000);

        return () => clearTimeout(timer);
    }, [mountainId]);

    return {
        selectedMountainId,
        selectedCourseId,
        selectedWeekdayId,
        mountainOptions,
        courseOptions,
        mouuntainChangeHandler,
        courseChangeHandler,
        weekdayChangeHandler,
        isLoading,
        mountainTitle,
        mountainDescription,
        errorMessage,
        setErrorMessage,
        submitHandler,
        weekdayData,
    };
}

const weekdayData = [
    { id: 1, name: '오늘' },
    { id: 2, name: '내일' },
    { id: 3, name: '모레' },
];
