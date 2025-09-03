import { useRef, useState } from 'react';
import { useQueryClient, type InfiniteData } from '@tanstack/react-query';
import useApiMutation from './useApiMutation';
import { validateAccessToken } from '../utils/utils';
import type { CardData } from '../types/reportCardTypes';

type ReportPages = InfiniteData<CardData[]>;

interface UseCardLikeMutationProps {
    reportId: number | null;
    queryKey: unknown[];
}

export default function useCardLikeMutation({
    reportId,
    queryKey,
}: UseCardLikeMutationProps) {
    const [validationError, setValidationError] = useState<string>('');
    const queryClient = useQueryClient();
    const previousDataRef = useRef<ReportPages | undefined>(undefined);

    const cardLikeMutation = useApiMutation(
        `/card/interaction/report/like/${reportId}`,
        'POST',
        {
            onMutate: async () => {
                await queryClient.cancelQueries({ queryKey });
                const previousData = queryClient.getQueryData(queryKey);
                previousDataRef.current = previousData as ReportPages;

                queryClient.setQueryData(queryKey, (oldData: ReportPages) => {
                    if (!oldData) return oldData;

                    const newPages = oldData.pages.map((page: CardData[]) => {
                        return page.map((card: CardData) => {
                            if (card.reportId !== reportId) return card;

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
                    queryClient.setQueryData(queryKey, previousDataRef.current);
                }

                if (e instanceof TypeError) {
                    setValidationError('네트워크 연결을 확인해주세요.');
                    return;
                }

                setValidationError('좋아요 요청에 실패했습니다.');
            },
        },
    );

    const heartClickHandler = () => {
        if (!validateAccessToken()) {
            setValidationError('로그인이 필요합니다.');
            return;
        }

        cardLikeMutation.mutate({});
    };

    return {
        heartClickHandler,
        validationError,
        isPending: cardLikeMutation.isPending,
    };
}
