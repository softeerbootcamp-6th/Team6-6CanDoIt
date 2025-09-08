import { css, keyframes } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { useState } from 'react';

import useApiMutation from '../../../hooks/useApiMutation.ts';
import useForecastCardData from '../../../hooks/useForecastCardData.ts';

import Icon from '../../atoms/Icon/Icons.tsx';
import WeatherSummaryCardHeader from '../../molecules/Forecast/WeatherSummaryCardHeader.tsx';
import LoginRequiredModal from '../../molecules/Modal/LoginRequiredModal.tsx';
import FrontWeatherSummaryCard from './FrontWeatherSummaryCard.tsx';
import BackWeatherSummaryCard from './BackWeatherSummaryCard.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import { validateAccessToken } from '../../../utils/utils.ts';

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
    const [isModal, setIsModal] = useState<boolean>(false);

    const { frontCard, backCard, isLoading, isError } = useForecastCardData(
        selectedCourseId,
        scrollSelectedTime,
    );

    const cardData = { frontCard, backCard };

    const storeMountainCardMutation = useApiMutation<any>(
        `/card/interaction/history/${selectedCourseId}`,
        'PUT',
        {
            onSuccess: () => {
                alert('최근 본 등산목록에 추가되었습니다.');
                onClose();
            },
            onError: () => {
                alert('잠시후 다시 시도해주세요.');
            },
        },
        { startDateTime: scrollSelectedTime },
    );

    const handleStoreButtonClick = (e: React.MouseEvent<HTMLButtonElement>) => {
        e.stopPropagation();
        const accessToken =
            localStorage.getItem('accessToken') ??
            sessionStorage.getItem('accessToken');
        if (!accessToken) {
            setErrorMessage('로그인이 필요한 서비스입니다.');
            return;
        }
        if (validateAccessToken()) {
            storeMountainCardMutation.mutate({});
        } else {
            setIsModal(true);
        }
    };

    if (isLoading)
        return (
            <div css={loadingStyles} role='dialog' aria-modal='true'>
                <Icon name='clear-day' width={2} height={2} color='grey-100' />
                <Icon name='rain' width={2} height={2} color='grey-100' />
                <Icon name='snow' width={2} height={2} color='grey-100' />
                <Icon
                    name='thunderstorm'
                    width={2}
                    height={2}
                    color='grey-100'
                />
            </div>
        );

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
                    handleStoreButtonClick(e);
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
                <LoginRequiredModal onClose={() => setErrorMessage('')} />
            )}
            {isError && (
                <Modal onClose={() => window.location.reload()}>
                    데이터 페칭중 오류가 발생했습니다. 새로고침을 통해 다시
                    시도해주세요.
                </Modal>
            )}
            {isModal && (
                <LoginRequiredModal
                    text='로그인 유효시간이 지났습니다.'
                    onClose={() => setIsModal(false)}
                />
            )}
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

const storeBtnStyles = css`
    all: unset;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    top: 28%;
    left: calc(50% + 13rem);
    width: 3rem;
    height: 3rem;
    border-radius: 100%;
    background-color: ${colors.grey[40]};
    padding: 0 3px 5px 0;
    box-sizing: border-box;
    cursor: pointer;
    &:hover {
        opacity: 0.8;
    }
`;

const show2 = keyframes`
  0%, 24.99% { opacity: 0; }
  25%, 99% { opacity: 1; }
  100% { opacity: 0; }
`;

const show3 = keyframes`
  0%, 49.99% { opacity: 0; }
  50%, 99% { opacity: 1; }
  100% { opacity: 0; }
`;

const show4 = keyframes`
  0%, 74.99% { opacity: 0; }
  75%, 99% { opacity: 1; }
  100% { opacity: 0; }
`;

const loadingStyles = css`
    position: absolute;
    inset: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    background-color: rgba(0, 0, 0, 0.1);
    z-index: 50;

    & > :nth-of-type(2) {
        animation: ${show2} 2s linear infinite;
    }
    & > :nth-of-type(3) {
        animation: ${show3} 2s linear infinite;
    }
    & > :nth-of-type(4) {
        animation: ${show4} 2s linear infinite;
    }
`;
