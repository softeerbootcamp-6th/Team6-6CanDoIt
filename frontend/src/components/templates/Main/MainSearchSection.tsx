import SearchBar from '../../organisms/Common/SearchBar.tsx';
import { useEffect, useState } from 'react';
import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import {
    createHandleSubmit,
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from './utils/utils.ts';
import { useNavigate } from 'react-router-dom';

interface PropsState {
    onFormValidChange: (isValid: boolean) => void;
}

interface Option {
    id: string;
    name: string;
}

export default function MainSearchSection(props: PropsState) {
    const { onFormValidChange } = props;
    const [coursesData, setCoursesData] = useState<MountainCourse[]>([]);
    const [mountainsData, setMountainsData] = useState<MountainData[]>([]);
    useEffect(() => {
        setMountainsData(MountainsData);
        setCoursesData(initCoursesData);
    }, []);

    const mountainOptions: Option[] =
        refactorMountainDataToOptions(mountainsData);

    const courseOptions: Option[] = refactorCoursesDataToOptions(coursesData);

    const navigate = useNavigate();
    const handleSubmit = createHandleSubmit({
        onFormValidChange,
        navigate,
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
            weekdayOptions={weekdayData}
            onSubmit={handleSubmit}
            onMountainChange={handleMountainChange}
        />
    );
}

const initCoursesData = [{ courseId: '1', courseName: '산을 선택해주세요' }];

const MountainsData = [
    {
        mountainId: '1',
        mountainName: '태백산',
        mountainImageUrl: 'https://cdn.example.com/images/taebaek.png',
        mountainDescription: '한겨울 설경이 아름다운 산입니다.',
        weatherMetric: {
            precipitationType: 'NONE',
            sky: 'SUNNY',
            surfaceTemperature: 23.5,
            topTemperature: 18.2,
        },
    },
    {
        mountainId: '2',
        mountainName: '지리산',
        mountainImageUrl: 'https://cdn.example.com/images/jiri.png',
        mountainDescription: '한국에서 두 번째로 높은 산입니다.',
        weatherMetric: {
            precipitationType: 'RAIN',
            sky: 'OVERCAST',
            surfaceTemperature: 20.1,
            topTemperature: 15.3,
        },
    },
    {
        mountainId: '3',
        mountainName: '백두산',
        mountainImageUrl: 'https://cdn.example.com/images/taebaek.png',
        mountainDescription: '한겨울 설경이 아름다운 산입니다.',
        weatherMetric: {
            precipitationType: 'NONE',
            sky: 'SUNNY',
            surfaceTemperature: 23.5,
            topTemperature: 18.2,
        },
    },
    {
        mountainId: '4',
        mountainName: '한라산',
        mountainImageUrl: 'https://cdn.example.com/images/jiri.png',
        mountainDescription: '한국에서 두 번째로 높은 산입니다.',
        weatherMetric: {
            precipitationType: 'RAIN',
            sky: 'OVERCAST',
            surfaceTemperature: 20.1,
            topTemperature: 15.3,
        },
    },
    {
        mountainId: '5',
        mountainName: '설악산',
        mountainImageUrl: 'https://cdn.example.com/images/taebaek.png',
        mountainDescription: '한겨울 설경이 아름다운 산입니다.',
        weatherMetric: {
            precipitationType: 'NONE',
            sky: 'SUNNY',
            surfaceTemperature: 23.5,
            topTemperature: 18.2,
        },
    },
    {
        mountainId: '6',
        mountainName: '가야산',
        mountainImageUrl: 'https://cdn.example.com/images/jiri.png',
        mountainDescription: '한국에서 두 번째로 높은 산입니다.',
        weatherMetric: {
            precipitationType: 'RAIN',
            sky: 'OVERCAST',
            surfaceTemperature: 20.1,
            topTemperature: 15.3,
        },
    },
];

const weekdayData = [
    { id: 'today', name: '오늘' },
    { id: 'tomorrow', name: '내일' },
    { id: 'dayaftertomorrow', name: '글피' },
];
