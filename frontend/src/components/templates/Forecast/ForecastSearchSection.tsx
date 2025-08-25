import SearchBar from '../../organisms/Common/SearchBar';
import { useSearchParams } from 'react-router-dom';
import { useEffect, useMemo, useState } from 'react';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../Main/utils.ts';

interface Option {
    id: number;
    name: string;
}

export default function ForecastSearchSection() {
    const [searchParams, setSearchParams] = useSearchParams();
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));
    const weekdayId = Number(searchParams.get('weekdayid'));

    const [selectedMountainId, setSelectedMountainId] =
        useState<number>(mountainId);
    const [selectedCourseId, setSelectedCourseId] = useState<number>(courseId);
    const [selectedWeekdayId, setSelectedWeekdayId] =
        useState<number>(weekdayId);

    const { data: coursesData } = useApiQuery<MountainCourse[]>(
        `/card/mountain/${selectedMountainId}/course`,
        {},
        {
            enabled: selectedMountainId !== 0,
            retry: false,
        },
    );
    const { data: mountainsData } = useApiQuery<MountainData[]>(
        '/card/mountain',
        {},
        {
            retry: false,
        },
    );

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
        if (courseId !== 0) return;
        const newCourseId =
            selectedCourseId !== 0
                ? selectedCourseId
                : (coursesData?.[0].courseId ?? 0);
        setSelectedCourseId(newCourseId);
        const next = new URLSearchParams(searchParams);
        next.set('courseid', String(newCourseId));
        setSearchParams(next);
    }, [coursesData]);
    useEffect(() => {
        if (weekdayId !== 0) return;
        setSelectedWeekdayId(weekdayData[0].id);
        const next = new URLSearchParams(searchParams);
        next.set('weekdayid', String(weekdayData[0].id));
        setSearchParams(next);
    }, []);

    return (
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
    );
}

const weekdayData = [
    { id: 1, name: '오늘' },
    { id: 2, name: '내일' },
    { id: 3, name: '모레' },
];
