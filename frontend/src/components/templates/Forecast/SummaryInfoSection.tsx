import MountainInfoPreview from '../../molecules/Forecast/MountainInfoPreview.tsx';
import { css } from '@emotion/react';
import ScrollIndicator from '../../molecules/Forecast/ScrollIndicator.tsx';

const dummyData = {
    courseImageUrl: 'https://cdn.example.com/images/course01.png',
    totalDuration: 2.5,
    totalDistance: 4.8,
    weatherMetric: {
        precipitationType: 'NONE',
        sky: 'CLOUDY',
        surfaceTemperature: 21.3,
        topTemperature: 17.0,
    },
    hikingActivityStatus: '좋음',
};

export default function SummaryInfoSection() {
    return (
        <div css={wrapperStyles}>
            <MountainInfoPreview
                totalDuration={dummyData.totalDuration}
                totalDistance={dummyData.totalDistance}
                courseImageUrl={dummyData.courseImageUrl}
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
