import MountainInfoPreview from '../../molecules/Forecast/MountainInfoPreview.tsx';
import { css } from '@emotion/react';
import ScrollIndicator from '../../molecules/Forecast/ScrollIndicator.tsx';
import { useState, useEffect } from 'react';

const placeholderData = {
    courseImageUrl: 'https://cdn.example.com/images/course01.png',
    totalDuration: 2.5,
    totalDistance: 4.8,
    sunrise: '09:00:00',
    sunset: '09:00:00',
    hikingActivityStatus: '좋음',
} as const;

interface MountainCourseData {
    courseImageUrl: string;
    totalDuration: number;
    totalDistance: number;
    sunrise: string;
    sunset: string;
    hikingActivityStatus: HikingActivityStatus;
}

type HikingActivityStatus = '좋음' | '매우좋음' | '나쁨' | '보통';

export default function SummaryInfoSection() {
    const [mountainCourseData, setMountainCourseData] =
        useState<MountainCourseData>(placeholderData);

    useEffect(() => {
        setMountainCourseData(placeholderData);
    }, []);

    const { totalDuration, totalDistance, courseImageUrl, sunrise, sunset } =
        mountainCourseData;

    return (
        <div css={wrapperStyles}>
            <MountainInfoPreview
                totalDuration={totalDuration}
                totalDistance={totalDistance}
                courseImageUrl={courseImageUrl}
                sunriseTime={sunrise.slice(0, 5)}
                sunsetTime={sunset.slice(0, 5)}
            />
            <ScrollIndicator />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    height: calc(100dvh - 12.5rem);
    justify-content: space-between;
    box-sizing: border-box;
    padding-bottom: 3rem;
`;
