import SearchBar from '../../organisms/Common/SearchBar';
import { useSearchParams } from 'react-router-dom';
import { useState } from 'react';
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

    const mountainOptions: Option[] = refactorMountainDataToOptions(
        mountainsData ?? [],
    );
    const courseOptions: Option[] = refactorCoursesDataToOptions(
        coursesData ?? [],
    );

    return (
        <SearchBar
            searchBarTitle='날씨예보 보기'
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
    { id: 3, name: '글피' },
];
