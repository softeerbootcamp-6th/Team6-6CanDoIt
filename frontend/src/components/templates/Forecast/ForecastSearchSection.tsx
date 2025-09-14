import SearchBar from '../../organisms/Common/SearchBar';
import { useSearchParams } from 'react-router-dom';
import { useEffect, useMemo, useRef, useState } from 'react';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../Main/utils.ts';
import Loading from '../../organisms/Loading/Loading.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';

interface Option {
    id: number;
    name: string;
}

export default function ForecastSearchSection() {
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

    return (
        <>
            <SearchBar
                searchBarMessage='를 오르는'
                pageName='main'
                mountainOptions={mountainOptions}
                selectedMountainId={selectedMountainId ?? 0}
                mountainChangeHandler={mouuntainChangeHandler}
                courseOptions={courseOptions}
                selectedCourseId={selectedCourseId ?? 0}
                courseChangeHandler={courseChangeHandler}
                weekdayOptions={weekdayData}
                selectedWeekdayId={selectedWeekdayId ?? 0}
                weekdayChangeHandler={weekdayChangeHandler}
                onSubmit={submitHandler}
            />
            {isLoading && (
                <Loading
                    mountainTitle={mountainTitle}
                    mountainDescription={mountainDescription}
                />
            )}
            {errorMessage && (
                <Modal
                    onClose={() => {
                        setErrorMessage('');
                    }}
                >
                    {errorMessage}
                </Modal>
            )}
        </>
    );
}

const weekdayData = [
    { id: 1, name: '오늘' },
    { id: 2, name: '내일' },
    { id: 3, name: '모레' },
];
