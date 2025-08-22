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
import { useNavigate, useSearchParams } from 'react-router-dom';
import useApiQuery from '../../../hooks/useApiQuery.ts';

interface PropsState {
    onFormValidChange: (isValid: boolean) => void;
}

interface Option {
    id: number;
    name: string;
}

export default function MainSearchSection(props: PropsState) {
    const { onFormValidChange } = props;
    const [searchParams] = useSearchParams();
    const selectedMountainId = Number(searchParams.get('mountainid'));

    const { data: mountainsData } = useApiQuery<MountainData[]>(
        '/card/mountain',
        {},
        {
            placeholderData: MountainsData,
            retry: false,
        },
    );
    const { data: coursesData } = useApiQuery<MountainCourse[]>(
        `/card/mountain/${selectedMountainId}/course`,
        {},
        {
            placeholderData: initCoursesData,
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

    const navigate = useNavigate();
    const submitHandler = (values: {
        mountainId: number;
        courseId?: number;
        weekdayId?: number;
    }) => {
        const { mountainId, courseId = null, weekdayId = null } = values;

        const error = validate(values);
        if (error) {
            onFormValidChange(false);
            return;
        }

        onFormValidChange(true);
        navigate(
            `/forecast?mountainid=${mountainId}&courseid=${courseId}&weekdayid=${weekdayId}`,
        );
    };

    return (
        <SearchBar
            searchBarTitle='어디 날씨를 확인해볼까요?'
            searchBarMessage='를 오르는'
            pageName='main'
            mountainOptions={mountainOptions}
            courseOptions={courseOptions}
            weekdayOptions={weekdayData}
            onSubmit={submitHandler}
        />
    );
}

const initCoursesData = [{ courseId: 0, courseName: '산을 선택해주세요' }];

const MountainsData = [
    {
        mountainId: 1,
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
        mountainId: 2,
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
        mountainId: 3,
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
        mountainId: 4,
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
        mountainId: 5,
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
        mountainId: 6,
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
    { id: 1, name: '오늘' },
    { id: 2, name: '내일' },
    { id: 3, name: '글피' },
];
