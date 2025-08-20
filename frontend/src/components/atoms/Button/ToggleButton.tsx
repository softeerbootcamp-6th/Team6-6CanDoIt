import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    onBgColor?: string;
    offBgColor?: string;
    onCircleColor?: string;
    offCircleColor?: string;
    isOn: boolean;
    onClick: () => void;
}

const { colors } = theme;

export default function ToggleButton({
    onBgColor = colors.grey[100],
    offBgColor = colors.grey[70],
    onCircleColor = colors.grey[40],
    offCircleColor = colors.grey[40],
    isOn,
    onClick,
}: PropsState) {
    return (
        <button
            css={buttonWrapperStyles(isOn, onBgColor, offBgColor)}
            onClick={onClick}
        >
            <div css={circleStyles(isOn, onCircleColor, offCircleColor)}></div>
        </button>
    );
}

const buttonWrapperStyles = (
    isOn: boolean,
    onBgColor: string,
    offBgColor: string,
) => css`
    all: unset;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding-left: 0.3rem;
    width: 3.2rem;
    height: 1.9rem;

    background-color: ${isOn ? onBgColor : offBgColor};
    border-radius: 1rem;
    transition: background-color 0.3s ease;
    cursor: pointer;
`;

const circleStyles = (
    isOn: boolean,
    onCircleColor: string,
    offCircleColor: string,
) => css`
    width: 1.6rem;
    height: 1.6rem;
    border-radius: 100%;
    background-color: ${isOn ? onCircleColor : offCircleColor};
    transform: ${isOn ? 'translateX(1.25rem)' : 'translateX(0)'};
    transition:
        transform 0.3s ease,
        background-color 0.3s ease;
`;
