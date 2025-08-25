import MountainCard from '../../organisms/Main/MountainCard.tsx';
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';
import { refactorMountainsData } from './utils.ts';
import type { MountainData } from '../../../types/mountainTypes';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import { useState } from 'react';

export default function MountainCardSection() {
    const navigate = useNavigate();
    const [hoveredCardId, setHoveredCardId] = useState<number | null>(null);

    const { data: mountainsData } = useApiQuery<MountainData[]>(
        '/card/mountain',
        {},
        {
            retry: false,
        },
    );

    const wheelHandler = (event: React.WheelEvent<HTMLDivElement>) => {
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };
    const cardClickHandler = (mountainId: number) => {
        navigate(`/forecast?mountainid=${mountainId}`);
    };
    const mounseEnterHandler = (mountainId: number) => {
        setHoveredCardId(mountainId);
    };
    const mouseLeaveHandler = () => {
        setHoveredCardId(null);
    };

    const data = refactorMountainsData(mountainsData ?? []);

    return (
        <div
            css={mountainCardContainerStyle}
            onWheel={wheelHandler}
            onMouseLeave={mouseLeaveHandler}
        >
            {data.map((mountain) => (
                <MountainCard
                    key={mountain.mountainId}
                    onClick={() => {
                        cardClickHandler(mountain.mountainId);
                    }}
                    onMouseEnter={() => mounseEnterHandler(mountain.mountainId)}
                    isHovered={hoveredCardId === mountain.mountainId}
                    isDimmed={
                        hoveredCardId !== null &&
                        hoveredCardId !== mountain.mountainId
                    }
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
    padding: 3rem 2rem 1.5rem 2rem;
    width: 100%;
    box-sizing: border-box;

    scrollbar-width: none;
    -ms-overflow-style: none;

    &::-webkit-scrollbar {
        display: none;
    }
`;
