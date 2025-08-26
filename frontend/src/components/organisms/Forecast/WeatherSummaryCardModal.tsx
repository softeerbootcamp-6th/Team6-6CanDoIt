import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import FrontWeatherSummaryCard from './FrontWeatherSummaryCard.tsx';
import { useState } from 'react';
import BackWeatherSummaryCard from './BackWeatherSummaryCard.tsx';
import WeatherSummaryCardHeader from '../../molecules/Forecast/WeatherSummaryCardHeader.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import { useNavigate } from 'react-router-dom';
import { useForecastCardData } from '../../../hooks/useForecastCardData.ts';

interface Props {
    onClose: () => void;
    scrollSelectedTime: string;
    selectedCourseId: number;
}

export default function WeatherSummaryCardModal({
    onClose,
    scrollSelectedTime,
    selectedCourseId,
}: Props) {
    const [isFront, setIsFront] = useState<boolean>(true);
    const [errorMessage, setErrorMessage] = useState<string>('');

    const {
        frontCard,
        backCard,
        isLoading,
        isError,
        error: cardError,
    } = useForecastCardData(selectedCourseId, scrollSelectedTime);

    const cardData = { frontCard, backCard };

    const navigate = useNavigate();
    const storeMountainCardMutation = useApiMutation<any>(
        `/card/interaction/history/${selectedCourseId}`,
        'PUT',
        {
            onSuccess: () => {
                alert('최근 본 등산목록에 추가되었습니다.');
                onClose();
            },
            onError: () => {
                if (
                    !(
                        localStorage.getItem('accessToken') ||
                        sessionStorage.getItem('accessToken')
                    )
                ) {
                    setErrorMessage('로그인이 필요한 서비스입니다.');
                } else {
                    alert('잠시후 다시 시도해주세요.');
                }
            },
        },
        { startDateTime: scrollSelectedTime },
    );

    if (isLoading) return <div>Loading...</div>;
    if (isError) return <div>{cardError?.message}</div>;

    return (
        <div css={overlayStyles}>
            <WeatherSummaryCardHeader />

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
            <button
                onClick={(e) => {
                    e.stopPropagation();
                    storeMountainCardMutation.mutate({});
                }}
                css={storeBtnStyles}
            >
                <Icon
                    name='download-02'
                    width={1.4}
                    height={1.4}
                    color='grey-100'
                />
            </button>
            {errorMessage && (
                <Modal
                    onClose={() => {
                        setErrorMessage('');
                    }}
                >
                    <div
                        css={css`
                            display: flex;
                            flex-direction: column;
                            gap: 2rem;
                            align-items: center;
                        `}
                    >
                        로그인이 필요한 서비스입니다.
                        <button
                            css={btnStyles}
                            onClick={() => navigate('/login')}
                        >
                            로그인으로 이동
                        </button>
                    </div>
                </Modal>
            )}
        </div>
    );
}

const { colors, typography } = theme;

const btnStyles = css`
    all: unset;
    width: 10rem;
    height: 2.5rem;
    border-radius: 1rem;
    background-color: ${colors.status.normal.excellent};
    color: ${colors.grey[100]};
    font-weight: ${typography.fontWeight.bold};
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
`;

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

const storeBtnStyles = css`
    all: unset;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    top: 28%;
    left: calc(50% + 14rem);
    width: 3rem;
    height: 3rem;
    border-radius: 100%;
    background-color: ${colors.grey[40]};
    padding: 0 3px 5px 0;
    box-sizing: border-box;
    cursor: pointer;
`;
