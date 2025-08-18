import MountainCard from '../../organisms/Main/MountainCard.tsx';
import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { refactorMountainsData } from './utils/utils.ts';
import type { MountainData } from '../../../types/mountainTypes';

export default function MountainCardSection() {
    const navigate = useNavigate();
    const [mountainsData, setMountainsData] = useState<MountainData[]>([]);
    useEffect(() => {
        setMountainsData(MountainsData);
    }, []);

    const handleWheel = (event: React.WheelEvent<HTMLDivElement>) => {
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };
    const handleCardClick = (mountainId: string) => {
        navigate(
            `/forecast?mountainid=${mountainId}&courseId=null&weekdayId=null`,
        );
    };

    const data = refactorMountainsData(mountainsData);

    return (
        <div css={mountainCardContainerStyle} onWheel={handleWheel}>
            {data.map((mountain) => (
                <MountainCard
                    key={mountain.mountainName}
                    onClick={() => {
                        handleCardClick(mountain.mountainId);
                    }}
                    {...mountain}
                />
            ))}
        </div>
    );
}

const mountainCardContainerStyle = css`
    display: flex;
    flex-direction: row;
    gap: 1rem;
    overflow-x: auto;
    padding: 1.5rem 2rem;
    width: 100%;
    box-sizing: border-box;

    scrollbar-width: none;
    -ms-overflow-style: none;

    &::-webkit-scrollbar {
        display: none;
    }
`;

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
