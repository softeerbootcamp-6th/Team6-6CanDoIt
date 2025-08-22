import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
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
                cardStyle(mountainImageUrl),
                isHovered && hoveredCardStyle,
                isDimmed && dimmedCardStyle,
            ]}
            onClick={onClick}
            onMouseEnter={onMouseEnter}
        >
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

const { colors } = theme;

const cardStyle = (mountainImageUrl: string) => css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    min-width: 18.75rem;
    width: 18.75rem;
    height: 36.25rem;
    box-sizing: border-box;
    background: ${getColor({ colors, colorString: 'grey-50' })};
    border-radius: 1.5rem;
    padding: 1.25rem;
    background-image: url(${mountainImageUrl});
    background-size: cover;

    transition:
        transform 0.5s ${easingFunctions.outBack},
        box-shadow 0.5s ${easingFunctions.outQuart},
        opacity 0.4s ${easingFunctions.outQuart};
    position: relative;
    opacity: 1;
    cursor: pointer;
    will-change: transform, opacity;
`;

const hoveredCardStyle = css`
    transform: translateY(-30px) scale(1.05);
    box-shadow: 0 20px 30px rgba(0, 0, 0, 0.15);
    z-index: 2;
    transition-delay: 0.05s;
`;

const dimmedCardStyle = css`
    opacity: 0.6;
    transform: scale(0.98);
`;
