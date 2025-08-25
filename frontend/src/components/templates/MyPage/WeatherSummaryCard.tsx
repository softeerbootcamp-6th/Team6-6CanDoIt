import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { useState } from 'react';
import FrontWeatherSummaryCard from '../../organisms/Forecast/FrontWeatherSummaryCard';
import BackWeatherSummaryCard from '../../organisms/Forecast/BackWeatherSummaryCard.tsx';
import { useForecastCardData } from '../../../hooks/useForecastCardData.ts';

interface Props {
    courseId: number;
    forecastDate: string;
    onClose: () => void;
}

export default function WeatherSummaryCardModal({
    onClose,
    courseId,
    forecastDate,
}: Props) {
    const [isFront, setIsFront] = useState<boolean>(true);
    const { frontCard, backCard } = useForecastCardData(courseId, forecastDate);
    const cardData = { frontCard, backCard };

    if (!cardData) return <div>loading...</div>;

    return (
        <div css={overlayStyles} onClick={onClose}>
            <div
                css={modalStyles(isFront, cardData.frontCard.mountainImageUrl)}
                onClick={(e) => {
                    e.stopPropagation();
                    setIsFront((prev) => !prev);
                }}
            >
                <div className='front'>
                    <FrontWeatherSummaryCard
                        cardData={cardData.frontCard}
                        onClose={onClose}
                    />
                </div>
                <div className='back'>
                    <BackWeatherSummaryCard cardData={cardData.backCard} />
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

const modalStyles = (isFront: boolean, mountainImageUrl?: string) => css`
    width: 22rem;
    height: 35rem;
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
        background: ${mountainImageUrl
            ? `linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url(${mountainImageUrl}) no-repeat center center / cover`
            : colors.grey[20]};
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
