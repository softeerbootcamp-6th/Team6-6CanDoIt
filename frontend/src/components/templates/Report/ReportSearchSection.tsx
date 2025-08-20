import SearchBar from '../../organisms/Common/SearchBar.tsx';
import { css } from '@emotion/react';
import SearchSectionChipsRow from '../../organisms/Report/SearchSectionChipsRow.tsx';
import { useEffect, useState } from 'react';
import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import {
    refactorCoursesDataToOptions,
    refactorMountainDataToOptions,
} from '../Main/utils/utils.ts';
import { useNavigate, useSearchParams } from 'react-router-dom';
import FilterModal from '../../organisms/Report/FilterModal.tsx';

interface Option {
    id: string;
    name: string;
}

export default function ReportSearchSection() {
    const [coursesData, setCoursesData] = useState<MountainCourse[]>([]);
    const [mountainsData, setMountainsData] = useState<MountainData[]>([]);
    const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
    const [filterAnchor, setFilterAnchor] = useState<HTMLElement | null>(null);
    const [searchParams] = useSearchParams();
    const mountainId = searchParams.get('mountainid');
    const courseId = searchParams.get('courseid');
    const navigate = useNavigate();

    useEffect(() => {
        setMountainsData(MountainsData);
        setCoursesData(initCoursesData);
    }, []);

    const mountainOptions: Option[] =
        refactorMountainDataToOptions(mountainsData);
    const courseOptions: Option[] = refactorCoursesDataToOptions(coursesData);

    const submitHandler = (values: {
        mountainId: string;
        courseId?: string;
        weekdayId?: string;
    }) => {
        navigate(
            `/report?mountainid=${values.mountainId}&courseid=${values.courseId}`,
        );
    };
    const mountainChangeHandler = (data: Option) => {
        // 선택한 산에 해당하는 코스 데이터를 가져올 예정, setCoursesData를 통해 상태 업데이트
    };
    const filterClickHandler = (e: React.MouseEvent<HTMLElement>) => {
        setFilterAnchor(e.currentTarget);
        setIsFilterModalOpen(true);
    };

    return (
        <div css={searchWrapperStyle}>
            {mountainId && courseId && <SearchSectionChipsRow hidden />}

            <div css={searchBarStyle}>
                <SearchBar
                    searchBarTitle='어디 날씨를 확인해볼까요?'
                    searchBarMessage='의 실시간 제보'
                    pageName='report'
                    mountainOptions={mountainOptions}
                    courseOptions={courseOptions}
                    onSubmit={submitHandler}
                    onMountainChange={mountainChangeHandler}
                />
            </div>

            {mountainId && courseId && (
                <SearchSectionChipsRow onFilterClick={filterClickHandler} />
            )}

            <FilterModal
                isOpen={isFilterModalOpen}
                onClose={() => setIsFilterModalOpen(false)}
                title='필터'
                description='원하는 날씨 조건을 선택하세요'
                filterColumn={filterColumn}
                anchorElement={filterAnchor}
            />
        </div>
    );
}

const searchWrapperStyle = css`
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding: 0 2rem;
    box-sizing: border-box;
`;

const searchBarStyle = css`
    width: 100%;
`;

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

const filterColumn = {
    weatherKeywords: [
        { id: 0, description: '화창해요' },
        { id: 1, description: '구름이 많아요' },
        { id: 2, description: '더워요' },
        { id: 3, description: '추워요' },
    ],
    rainKeywords: [
        { id: 0, description: '부슬비가 내려요' },
        { id: 1, description: '장대비가 쏟아져요' },
        { id: 2, description: '천둥 번개가 쳐요' },
        { id: 3, description: '폭우가 내려요' },
    ],
    etceteraKeywords: [
        { id: 0, description: '안개가 껴요' },
        { id: 1, description: '미세먼지가 많아요' },
        { id: 2, description: '시야가 흐려요' },
    ],
};
