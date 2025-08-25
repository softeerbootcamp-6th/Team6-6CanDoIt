import { LabelHeading } from '../../atoms/Heading/Heading.tsx';
import {
    filterGatherer,
    formatTimeDifference,
    getCurrentTime,
} from '../Report/utils.ts';
import FrontReportCard from '../../organisms/Report/FrontReportCard.tsx';
import BackReportCard from '../../organisms/Report/BackReportCard.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import { css } from '@emotion/react';
import useApiInfiniteQuery from '../../../hooks/useApiInfiniteQuery.ts';
import { useRef, useState } from 'react';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import { type InfiniteData, useQueryClient } from '@tanstack/react-query';
import { validateAccessToken } from '../../../utils/utils.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import { theme } from '../../../theme/theme.ts';

type ReportPages = InfiniteData<CardData[]>;

interface CardData {
    reportId: number;
    reportType: string;
    createdAt: string;
    nickname: string;
    userImageUrl: string;
    imageUrl: string;
    content: string;
    likeCount?: number;
    isLiked?: boolean;
    weatherKeywords?: string[];
    rainKeywords?: string[];
    etceteraKeywords?: string[];
}

export default function MyLikeSection() {
    const title = '좋아요한 제보 목록';
    const pageSize = 5;
    const [flippedCardId, setFlippedCardId] = useState<number | null>(null);
    const [validationError, setValidationError] = useState<string>('');

    const {
        data: cardsData,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
    } = useApiInfiniteQuery<CardData>(
        `/card/interaction/report/me/like`,
        {
            pageSize,
            idField: 'reportId',
        },
        {
            retry: false,
        },
    );
    const flattenedData = cardsData?.pages.flat();

    const queryClient = useQueryClient();
    const key = [
        `/card/interaction/report/me/like`,
        {
            pageSize,
            idField: 'reportId',
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
            onSuccess: () => {
                console.log('Like action successful');
            },
            onError: (e) => {
                console.error('Report submission failed:', e);
                if (previousDataRef.current) {
                    queryClient.setQueryData(key, previousDataRef.current);
                }
            },
        },
    );

    const currentTime = getCurrentTime();

    return (
        <div css={containerStyle}>
            <div css={reportTitleStyle}>
                <LabelHeading HeadingTag='h2'>{title}</LabelHeading>
            </div>
            <div css={reportCardContainerStyle(hasNextPage)}>
                {flattenedData?.map((card) => {
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
                                    onClick={() =>
                                        setFlippedCardId(card.reportId)
                                    }
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
                                    onHeartClick={() => {
                                        if (!validateAccessToken()) {
                                            setValidationError(
                                                '로그인이 필요합니다.',
                                            );
                                            return;
                                        }
                                        cardLikeMutation.mutate({});
                                    }}
                                />
                            </div>
                        </div>
                    );
                })}
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
            {validationError && (
                <Modal onClose={() => setValidationError('')}>
                    {validationError}
                </Modal>
            )}
        </div>
    );
}

const { colors } = theme;

const containerStyle = css`
    display: flex;
    flex-direction: column;
    overflow-x: auto;
`;

const reportCardContainerStyle = (hasNextPage: boolean) => css`
    display: flex;
    flex-direction: row;
    gap: 1rem;
    overflow-x: auto;
    overflow-y: hidden;
    margin-top: 1rem;

    ${hasNextPage &&
    `mask-image: linear-gradient(to left, transparent 0%, black 5%);`}

    padding-bottom: 1rem;
    box-sizing: border-box;

    &::-webkit-scrollbar {
        height: 8px;
    }
    &::-webkit-scrollbar-thumb {
        background-color: ${colors.grey[100]};
        border-radius: 8px;
    }
    &::-webkit-scrollbar-track {
        background-color: ${colors.greyOpacityWhite[70]};
    }
`;

const reportTitleStyle = css`
    width: max-content;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 1.5rem;
`;
const loadMoreIconProps = {
    name: 'chevron-down',
    width: 2,
    height: 2,
    color: 'grey-100',
};

const loadMoreContainerStyle = css`
    display: flex;
    justify-content: center;
    align-items: center;
`;

const loadMoreButtonStyle = (isLoading: boolean) => css`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 3rem;
    height: 3rem;
    margin-right: 3rem;
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
