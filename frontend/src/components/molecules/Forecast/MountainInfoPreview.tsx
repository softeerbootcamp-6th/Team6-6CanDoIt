import { css } from '@emotion/react';
import SunSchedule from './SunSchedule.tsx';
import ClimbInfoText from './ClimbInfoText.tsx';

interface PropsState {
    totalDuration: number;
    totalDistance: number;
    courseImageUrl: string;
    sunriseTime: string;
    sunsetTime: string;
}

export default function MountainInfoPreview({
    totalDuration,
    totalDistance,
    courseImageUrl,
    sunriseTime,
    sunsetTime,
}: PropsState) {
    return (
        <div css={wrapperStyles}>
            <div css={dummySteyls}>
                <img src={courseImageUrl} />
            </div>

            <ClimbInfoText
                totalDistance={totalDistance}
                totalDuration={totalDuration}
            />
            <SunSchedule sunriseTime={sunriseTime} sunsetTime={sunsetTime} />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2.2rem;
    width: 38rem;
    height: 50dvh;
    margin-top: 2%;
`;

const dummySteyls = css`
    width: 100%;
    flex-grow: 1;
    display: flex;
    justify-content: center;
    background: red;
`;
