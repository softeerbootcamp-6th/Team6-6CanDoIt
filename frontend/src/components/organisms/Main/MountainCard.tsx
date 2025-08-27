import { css } from '@emotion/react';
import MountainCardFooter from '../../molecules/MountainCard/MountainCardFooter.tsx';
import MountainCardHeader from '../../molecules/MountainCard/MountainCardHeader.tsx';

interface PropsState {
    mountainName: string;
    mountainImageUrl: string;
    weatherIconName: string;
    surfaceTemperature: number;
    summitTemperature: number;
    onClick: () => void;
    onMouseEnter?: () => void;
    isHovered?: boolean;
    isDimmed?: boolean;
}

export default function MountainCard(props: PropsState) {
    const {
        mountainName,
        mountainImageUrl,
        weatherIconName,
        surfaceTemperature,
        summitTemperature,
        onClick,
        onMouseEnter,
        isHovered = false,
        isDimmed = false,
    } = props;

    return (
        <div
            css={[
                cardStyle,
                isHovered && hoveredCardStyle,
                isDimmed && dimmedCardStyle,
            ]}
            onClick={onClick}
            onMouseEnter={onMouseEnter}
        >
            <img
                src={mountainImageUrl}
                loading='lazy'
                alt={mountainName}
                css={bgImageStyle}
            />
            <MountainCardHeader
                weatherIconName={weatherIconName}
                surfaceTemperature={surfaceTemperature}
                summitTemperature={summitTemperature}
            />
            <MountainCardFooter mountainName={mountainName} />
        </div>
    );
}

const easingFunctions = {
    outQuart: 'cubic-bezier(0.165, 0.84, 0.44, 1)',
    outBack: 'cubic-bezier(0.175, 0.885, 0.32, 1.275)',
};

const cardStyle = css`
    position: relative;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    min-width: 18.75rem;
    width: 18.75rem;
    height: 36.25rem;
    box-sizing: border-box;
    border-radius: 1.5rem;
    padding: 1.25rem;
    background: #000;
    transition:
        transform 0.5s ${easingFunctions.outBack},
        box-shadow 0.5s ${easingFunctions.outQuart},
        opacity 0.4s ${easingFunctions.outQuart};
    opacity: 1;
    cursor: pointer;
    will-change: transform, opacity;

    &::after {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: linear-gradient(
            to bottom,
            rgba(0, 0, 0, 0.6) 0%,
            rgba(0, 0, 0, 0) 30%,
            rgba(0, 0, 0, 0) 70%,
            rgba(0, 0, 0, 0.6) 100%
        );
        z-index: -5;
        pointer-events: none;
    }
`;

const bgImageStyle = css`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    z-index: -10;
`;

const hoveredCardStyle = css`
    transform: translateY(-1.5rem) scale(1.05);
    box-shadow: 0 20px 30px rgba(0, 0, 0, 0.15);
    z-index: 10;
    transition-delay: 0.05s;
`;

const dimmedCardStyle = css`
    opacity: 0.6;
    transform: scale(0.98);
`;
