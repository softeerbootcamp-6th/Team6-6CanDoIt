import { HeadlineHeading } from '../../atoms/Heading/Heading.tsx';
import FrontReportCard from '../../organisms/Report/FrontReportCard.tsx';
import { css } from '@emotion/react';
import ChipButton from '../../molecules/Button/ChipButton.tsx';
import ToggleButton from '../../atoms/Button/ToggleButton.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import { type FormEvent, useEffect, useRef, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import BackReportCard from '../../organisms/Report/BackReportCard.tsx';
import ReportModal from '../../organisms/Report/ReportModal.tsx';
import {
    formatTimeDifference,
    getCurrentTime,
    filterGatherer,
    reportFormValidation,
} from './utils.ts';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import { theme } from '../../../theme/theme.ts';
import useApiInfiniteQuery from '../../../hooks/useApiInfiniteQuery.ts';
import { type InfiniteData, useQueryClient } from '@tanstack/react-query';
import {
    parseFilterFromUrl,
    validateAccessToken,
} from '../../../utils/utils.ts';
import ReportPendingModal from '../../molecules/Modal/ReportPendingModal.tsx';
import type { CardData } from '../../../types/reportCardTypes';
import LoginRequiredModal from '../../molecules/Modal/LoginRequiredModal.tsx';

type ReportType = 'WEATHER' | 'SAFE';
type ReportPages = InfiniteData<CardData[]>;

interface ReportFormData {
    courseId: number;
    type: ReportType;
    content: string;
    weatherKeywords: number[];
    rainKeywords: number[];
    etceteraKeywords: number[];
}

export default function ReportCardSection() {
    const [reportType, setReportType] = useState<ReportType>('WEATHER');
    const [flippedCardId, setFlippedCardId] = useState<number | null>(null);
    const [isReportModalOpen, setIsReportModalOpen] = useState(false);
    const [validationError, setValidationError] = useState<string>('');
    const [searchParams] = useSearchParams();
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));
    const pageSize = 10;

    const weatherKeywords = parseFilterFromUrl(searchParams, 'weatherKeywords');
    const rainKeywords = parseFilterFromUrl(searchParams, 'rainKeywords');
    const etceteraKeywords = parseFilterFromUrl(
        searchParams,
        'etceteraKeywords',
    );

    const title =
        reportType === 'WEATHER' ? '실시간 날씨 제보' : '실시간 안전 제보';
    const currentTime = getCurrentTime();

    const { data: filterColumn, isError: isFilterColumnError } = useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            retry: false,
            networkMode: 'always',
            staleTime: 1000 * 60 * 1000,
            gcTime: 1000 * 60 * 1000,
        },
    );

    const {
        data: cardsData,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
        isError: isCardsError,
    } = useApiInfiniteQuery<CardData>(
        `/card/interaction/report/${courseId}`,
        {
            params: {
                reportType,
                weatherKeywordIds: weatherKeywords,
                rainKeywordIds: rainKeywords,
                etceteraKeywordIds: etceteraKeywords,
            },
            pageSize,
            idField: 'reportId',
        },
        {
            retry: false,
            networkMode: 'always',
        },
    );
    useEffect(() => {
        const errorMessage = isFilterColumnError ? '키워드' : '제보 카드';
        if (isFilterColumnError || isCardsError) {
            setValidationError(`${errorMessage} 불러오기에 실패했습니다.`);
        }
    }, [isFilterColumnError, isCardsError]);
    const flattenedData = cardsData?.pages.flat();

    const reportMutation = useApiMutation<FormData, any>(
        `/card/interaction/report`,
        'POST',
        {
            onSuccess: async () => {
                await queryClient.invalidateQueries({
                    queryKey: [
                        `/card/interaction/report/${courseId}`,
                        {
                            reportType,
                            weatherKeywordIds: weatherKeywords,
                            rainKeywordIds: rainKeywords,
                            etceteraKeywordIds: etceteraKeywords,
                            pageSize,
                        },
                    ],
                });
                setIsReportModalOpen(false);
                setFlippedCardId(null);
            },
            onError: (error: any) => {
                setIsReportModalOpen(false);
                error instanceof TypeError
                    ? setValidationError('네트워크 오류가 발생했습니다.')
                    : setValidationError('제보에 실패했습니다.');
            },
        },
    );

    const queryClient = useQueryClient();
    const key = [
        `/card/interaction/report/${courseId}`,
        {
            reportType,
            weatherKeywordIds: weatherKeywords,
            rainKeywordIds: rainKeywords,
            etceteraKeywordIds: etceteraKeywords,
            pageSize,
        },
    ] as const;
    const previousDataRef = useRef<ReportPages | undefined>(undefined);
    const cardLikeMutation = useApiMutation(
        `/card/interaction/report/like/${flippedCardId}`,
        'POST',
        {
            onMutate: async () => {
                await queryClient.cancelQueries({
                    queryKey: key,
                });
                const previousData = queryClient.getQueryData(key);
                previousDataRef.current = previousData as ReportPages;
                queryClient.setQueryData(key, (oldData: ReportPages) => {
                    if (!oldData) return oldData;
                    const newPages = oldData.pages.map((page: CardData[]) => {
                        return page.map((card: CardData) => {
                            if (card.reportId !== flippedCardId) return card;
                            const wasLiked = card.isLiked === true;
                            const prev = card.likeCount ?? 0;
                            const next = Math.max(
                                0,
                                prev + (wasLiked ? -1 : 1),
                            );
                            return {
                                ...card,
                                likeCount: next,
                                isLiked: !wasLiked,
                            };
                        });
                    });
                    return { ...oldData, pages: newPages };
                });
            },
            onError: (e: any) => {
                if (previousDataRef.current) {
                    queryClient.setQueryData(key, previousDataRef.current);
                }
                if (e instanceof TypeError) {
                    setValidationError('네트워크 연결을 확인해주세요.');
                    return;
                }
                setValidationError('좋아요 요청에 실패했습니다.');
            },
        },
    );

    const reportModalSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const form = e.currentTarget;
        const formData = new FormData(form);

        const validationErr = reportFormValidation(formData);
        setValidationError(validationErr);
        if (validationErr) return;

        if (!validateAccessToken()) {
            setValidationError('로그인이 필요합니다.');
            return;
        }

        const ids = (keyword: string) =>
            formData.getAll(keyword).map((id) => Number(id));

        const requestData: ReportFormData = {
            courseId: Number(courseId),
            type: reportType,
            content: formData.get('content') as string,
            weatherKeywords: ids('weatherKeywords'),
            rainKeywords: ids('rainKeywords'),
            etceteraKeywords: ids('etceteraKeywords'),
        };
        const imageFile = formData.get('image') as File;

        const payload = new FormData();
        payload.append(
            'request',
            new Blob([JSON.stringify(requestData)], {
                type: 'application/json',
            }),
        );
        payload.append('imageFile', imageFile);
        reportMutation.mutate(payload);
    };

    const reportCardSectionToggleButtonHandler = () => {
        setReportType((prev) => (prev === 'WEATHER' ? 'SAFE' : 'WEATHER'));
    };

    const wheelHandler = (event: React.WheelEvent<HTMLDivElement>) => {
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };

    const chipButtonClickHandler = () => {
        if (!validateAccessToken()) {
            setValidationError('로그인이 필요합니다.');
            return;
        }
        setIsReportModalOpen(true);
    };

    const heartClickHandler = () => {
        if (!validateAccessToken()) {
            setValidationError('로그인이 필요합니다.');
            return;
        }
        cardLikeMutation.mutate({});
    };

    if (!mountainId || !courseId || mountainId === 0 || courseId === 0)
        return null;

    const renderReportCards = () =>
        flattenedData?.map((card) => {
            const isFlipped = flippedCardId === card.reportId;
            const filterLabels = filterGatherer({
                weatherKeywords: card.weatherKeywords,
                rainKeywords: card.rainKeywords,
                etceteraKeywords: card.etceteraKeywords,
            });
            const timeAgo = formatTimeDifference({
                pastISO: card.createdAt,
                nowDate: currentTime,
            });

            return (
                <div
                    key={card.reportId}
                    css={[card3DStyle, isFlipped && card3DFlippedStyle]}
                >
                    <div css={cardFaceFrontStyle}>
                        <FrontReportCard
                            comment={card.content}
                            timeAgo={timeAgo}
                            filterLabels={filterLabels}
                            onClick={() => setFlippedCardId(card.reportId)}
                            imgSrc={card.imageUrl}
                            userImageUrl={card.userImageUrl}
                        />
                    </div>
                    <div css={cardFaceBackStyle}>
                        <BackReportCard
                            comment={card.content}
                            timeAgo={timeAgo}
                            userImageUrl={card.userImageUrl}
                            likeCount={card.likeCount}
                            filterLabels={filterLabels}
                            onClick={() => setFlippedCardId(null)}
                            isLiked={card.isLiked}
                            onHeartClick={heartClickHandler}
                        />
                    </div>
                </div>
            );
        });

    const renderModals = () => (
        <>
            <ReportModal
                title='실시간 제보하기'
                description='사진, 내용, 키워드를 선택하여 제보해주세요.'
                filterColumn={filterColumn ?? []}
                isOpen={isReportModalOpen}
                onClose={() => setIsReportModalOpen(false)}
                onSubmit={reportModalSubmitHandler}
            />
            {validationError === '로그인이 필요합니다.' ? (
                <LoginRequiredModal onClose={() => setValidationError('')} />
            ) : (
                validationError && (
                    <Modal onClose={() => setValidationError('')}>
                        {validationError}
                    </Modal>
                )
            )}
            {reportMutation.isPending && <ReportPendingModal />}
        </>
    );

    return (
        <>
            <div css={reportTitleStyle}>
                <HeadlineHeading HeadingTag='h2'>{title}</HeadlineHeading>
                <ToggleButton
                    isOn={reportType === 'SAFE'}
                    onClick={reportCardSectionToggleButtonHandler}
                    offBgColor={colors.grey[30]}
                    onBgColor={colors.grey[30]}
                    onCircleColor={colors.status.normal.good}
                    offCircleColor={colors.status.normal.bad}
                />
                <ChipButton
                    onClick={chipButtonClickHandler}
                    text='제보하기'
                    iconName='edit-03'
                />
                {renderModals()}
            </div>
            <div css={reportCardContainerStyle} onWheel={wheelHandler}>
                {renderReportCards()}
                <div css={loadMoreContainerStyle}>
                    {hasNextPage && (
                        <button
                            css={loadMoreButtonStyle(isFetchingNextPage)}
                            onClick={() => fetchNextPage()}
                            disabled={isFetchingNextPage}
                        >
                            <Icon {...loadMoreIconProps} />
                        </button>
                    )}
                </div>
            </div>
        </>
    );
}

const loadMoreIconProps = {
    name: 'chevron-down',
    width: 2,
    height: 2,
    color: 'grey-100',
};

const { colors } = theme;

const loadMoreContainerStyle = css`
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 1rem;
`;

const loadMoreButtonStyle = (isLoading: boolean) => css`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 3rem;
    height: 3rem;
    background: none;
    border: none;
    cursor: ${isLoading ? 'default' : 'pointer'};
    opacity: ${isLoading ? 0.5 : 1};
    transform: rotate(-90deg) scale(1.3);
    transition:
        opacity 200ms ease,
        transform 200ms ease;

    &:hover {
        transform: ${isLoading ? 'none' : 'scale(1.5) rotate(-90deg)'};
    }

    &:disabled {
        opacity: 0.5;
        cursor: default;
    }
`;

const card3DStyle = css`
    display: grid;
    grid-auto-rows: 1fr;
    transform-style: preserve-3d;
    transition: transform 0.6s ease;
    will-change: transform;
    width: max-content;
`;

const card3DFlippedStyle = css`
    transform: rotateY(180deg);
`;

const faceBase = css`
    grid-area: 1 / 1;
    backface-visibility: hidden;
`;

const cardFaceFrontStyle = css`
    ${faceBase};
`;

const cardFaceBackStyle = css`
    ${faceBase};
    transform: rotateY(180deg);
`;

const reportCardContainerStyle = css`
    display: flex;
    flex-direction: row;
    gap: 1rem;
    overflow-x: auto;
    overflow-y: hidden;
    margin-left: 2rem;
    margin-top: 2rem;

    scrollbar-width: none;
    -ms-overflow-style: none;

    &::-webkit-scrollbar {
        display: none;
    }
`;

const reportTitleStyle = css`
    width: max-content;
    margin-top: 1rem;
    margin-left: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 1.5rem;
`;
