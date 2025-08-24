import { HeadlineHeading } from '../../atoms/Heading/Heading.tsx';
import FrontReportCard from '../../organisms/Report/FrontReportCard.tsx';
import { css } from '@emotion/react';
import ChipButton from '../../molecules/Button/ChipButton.tsx';
import ToggleButton from '../../atoms/Button/ToggleButton.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import { type FormEvent, useRef, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import BackReportCard from '../../organisms/Report/BackReportCard.tsx';
import ReportModal from '../../organisms/Report/ReportModal.tsx';
import {
    formatTimeDifference,
    getCurrentTime,
    filterGatherer,
} from './utils.ts';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import { theme } from '../../../theme/theme.ts';
import useApiInfiniteQuery from '../../../hooks/useApiInfiniteQuery.ts';
import { type InfiniteData, useQueryClient } from '@tanstack/react-query';
import { validateAccessToken } from '../../../utils/utils.ts';
import ReportPendingModal from '../../molecules/Modal/ReportPendingModal.tsx';

type ReportType = 'WEATHER' | 'SAFE';
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

    const parseFilterFromUrl = (
        searchParams: URLSearchParams,
        key: string,
    ): number[] => {
        const paramValue = searchParams.get(key);
        if (!paramValue) return [];

        try {
            return paramValue
                .replace(/[\[\]]/g, '')
                .split(',')
                .filter((id) => id !== '')
                .map((id) => Number(id));
        } catch (e) {
            console.error(`Failed to parse filter ${key}:`, e);
            return [];
        }
    };

    const weatherKeywords = parseFilterFromUrl(searchParams, 'weatherKeywords');
    const rainKeywords = parseFilterFromUrl(searchParams, 'rainKeywords');
    const etceteraKeywords = parseFilterFromUrl(
        searchParams,
        'etceteraKeywords',
    );

    const title =
        reportType === 'WEATHER' ? '실시간 날씨 제보' : '실시간 안전 제보';
    const currentTime = getCurrentTime();

    const { data: filterColumn } = useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            retry: false,
        },
    );

    const {
        data: cardsData,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
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
        },
    );
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
                    ? setValidationError(
                          '네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.',
                      )
                    : setValidationError(error.message);
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

    const formValidation = (formData: FormData) => {
        const imageFile = formData.get('image') as File;
        if (!imageFile || !imageFile.type.startsWith('image/')) {
            return '이미지 파일을 업로드해주세요';
        }

        const content = formData.get('content') as string;
        if (!content || content.trim() === '') {
            return '제보 내용을 입력해주세요';
        }

        const weatherKeywords = formData.getAll('weatherKeywords');
        const rainKeywords = formData.getAll('rainKeywords');
        const etceteraKeywords = formData.getAll('etceteraKeywords');
        if (
            weatherKeywords.length === 0 &&
            rainKeywords.length === 0 &&
            etceteraKeywords.length === 0
        ) {
            return '키워드를 선택해주세요';
        }

        return '';
    };

    const reportModalSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const form = e.currentTarget;
        const formData = new FormData(form);

        const validationErr = formValidation(formData);
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

    if (!mountainId || !courseId || mountainId === 0 || courseId === 0)
        return null;

    return (
        <>
            <div css={reportTitleStyle}>
                <HeadlineHeading HeadingTag='h2'>{title}</HeadlineHeading>
                <ToggleButton
                    isOn={reportType === 'SAFE'}
                    onClick={() => reportCardSectionToggleButtonHandler()}
                    offBgColor={colors.grey[100]}
                    onCircleColor={colors.status.normal.good}
                    offCircleColor={colors.status.normal.bad}
                />
                <ChipButton
                    onClick={() => {
                        if (!validateAccessToken()) {
                            setValidationError('로그인이 필요합니다.');
                            return;
                        }
                        setIsReportModalOpen(true);
                    }}
                    text='제보하기'
                    iconName='edit-03'
                />
                <ReportModal
                    title='실시간 날씨 제보하기'
                    description='실시간 날씨를 제보하려면 코스 근처에 있어야 해요. 사진은 6시간 이내에 촬영된 사진만 업로드 가능해요.'
                    filterColumn={filterColumn ?? []}
                    isOpen={isReportModalOpen}
                    onClose={() => setIsReportModalOpen(false)}
                    onSubmit={reportModalSubmitHandler}
                />
                {validationError && (
                    <Modal onClose={() => setValidationError('')}>
                        {validationError}
                    </Modal>
                )}
                {reportMutation.isPending && <ReportPendingModal />}
            </div>
            <div css={reportCardContainerStyle} onWheel={wheelHandler}>
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
