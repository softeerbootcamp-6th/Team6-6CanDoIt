import SearchBar from '../../organisms/Common/SearchBar.tsx';
import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
    validate,
} from './utils.ts';
import { useNavigate } from 'react-router-dom';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import { useState } from 'react';

interface PropsState {
    onFormValidChange: (isValid: boolean) => void;
}

interface Option {
    id: number;
    name: string;
}

export default function MainSearchSection(props: PropsState) {
    const { onFormValidChange } = props;

    const [selectedMountainId, setSelectedMountainId] = useState<number>(0);
    const [selectedCourseId, setSelectedCourseId] = useState<number>(0);
    const [selectedWeekdayId, setSelectedWeekdayId] = useState<number>(0);

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
    const navigate = useNavigate();

    const submitHandler = (e: React.FormEvent) => {
        e.preventDefault();

        const error = validate({
            mountainId: selectedMountainId,
            courseId: selectedCourseId,
            weekdayId: selectedWeekdayId,
        });
        if (error) {
            onFormValidChange(false);
            return;
        }

        onFormValidChange(true);
        navigate(
            `/forecast?mountainid=${selectedMountainId}&courseid=${selectedCourseId}&weekdayid=${selectedWeekdayId}`,
        );
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
            enabled: !!selectedMountainId,
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
            searchBarTitle='어디 날씨를 확인해볼까요?'
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
