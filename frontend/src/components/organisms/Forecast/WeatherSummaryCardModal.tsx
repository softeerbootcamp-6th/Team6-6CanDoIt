import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import FrontWeatherSummaryCard from './FrontWeatherSummaryCard.tsx';
import { useState } from 'react';
import BackWeatherSummaryCard from './BackWeatherSummaryCard.tsx';
import WeatherSummaryCardHeader from '../../molecules/Forecast/WeatherSummaryCardHeader.tsx';

interface Props {
    onClose: () => void;
}

export default function WeatherSummaryCardModal({ onClose }: Props) {
    const [isFront, setIsFront] = useState<boolean>(true);

    return (
        <div css={overlayStyles} onClick={onClose}>
            <WeatherSummaryCardHeader />

            <div
                css={modalStyles(isFront)}
                onClick={(e) => {
                    e.stopPropagation();
                    setIsFront((prev) => !prev);
                }}
            >
                <div className='front'>
                    <FrontWeatherSummaryCard onClose={onClose} />
                </div>
                <div className='back'>
                    <BackWeatherSummaryCard />
                </div>
            </div>
        </div>
    );
}

const { colors } = theme;

const overlayStyles = css`
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: ${colors.greyOpacity[10]};
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    z-index: 2000;
`;

const modalStyles = (isFront: boolean) => css`
    width: 20rem;
    height: 34rem;
    cursor: pointer;
    position: relative;
    transform-style: preserve-3d;
    transition: transform 0.8s cubic-bezier(0.77, 0, 0.175, 1);
    transform: ${isFront ? 'rotateY(0deg)' : 'rotateY(180deg)'};

    .front,
    .back {
        position: absolute;
        width: 100%;
        height: 100%;
        border-radius: 2rem;
        box-sizing: border-box;
        backface-visibility: hidden;
        top: 0;
        left: 0;
    }

    .front {
        background: ${colors.grey[20]};
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 1.5rem;
    }

    .back {
        background: ${colors.grey[100]};
        transform: rotateY(180deg);
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 1.5rem;
    }
`;
