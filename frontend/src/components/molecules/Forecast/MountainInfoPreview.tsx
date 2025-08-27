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
            <div css={imgContainerStyles}>
                <div css={gradientStyles}></div>
                <img src={courseImageUrl} css={imgStyles} />
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

const imgContainerStyles = css`
    width: 50%;
    height: 60%;
    flex-grow: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
`;

const gradientStyles = css`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: radial-gradient(
        circle,
        rgba(0, 0, 0, 0.25) 0%,
        rgba(0, 0, 0, 0.1) 30%,
        rgba(0, 0, 0, 0) 60%
    );
    z-index: 0; // 이미지 뒤로 보내기
`;

const imgStyles = css`
    width: 100%;
    height: 100%;
    object-fit: contain;
    position: relative;
    z-index: 1;
`;
