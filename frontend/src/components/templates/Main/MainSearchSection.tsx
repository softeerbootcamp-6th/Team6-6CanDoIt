import SearchBar from '../../organisms/Common/SearchBar.tsx';
import { useState } from 'react';
import type {
    MountainCourse,
    MountainData,
    SelectedMountainData,
} from '../../../types/mountainTypes';
import {
    createHandleSubmit,
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from './utils/utils.ts';

interface PropsState {
    mountainsData: MountainData[];
    onSearchClick: (data: SelectedMountainData) => void;
    onFormValidChange: (isValid: boolean) => void;
}

interface Option {
    id: string;
    name: string;
}

export default function MainSearchSection(props: PropsState) {
    const { mountainsData, onSearchClick, onFormValidChange } = props;
    const [coursesData, setCoursesData] =
        useState<MountainCourse[]>(initCoursesData);

    const mountainOptions: Option[] =
        refactorMountainDataToOptions(mountainsData);

    const courseOptions: Option[] = refactorCoursesDataToOptions(coursesData);

    const handleSubmit = createHandleSubmit({
        mountainsData,
        onSearchClick,
        onFormValidChange,
    });
    const handleMountainChange = (data: Option) => {
        // 선택한 산에 해당하는 코스 데이터를 가져올 예정, setCoursesData를 통해 상태 업데이트
    };

    return (
        <SearchBar
            searchBarTitle='어디 날씨를 확인해볼까요?'
            searchBarMessage='를 오르는'
            pageName='main'
            mountainOptions={mountainOptions}
            courseOptions={courseOptions}
            onSubmit={handleSubmit}
            onMountainChange={handleMountainChange}
        />
    );
}

const initCoursesData = [{ courseId: '1', courseName: '산을 선택해주세요' }];
