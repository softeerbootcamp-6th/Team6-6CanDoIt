import useApiInfiniteQuery from './useApiInfiniteQuery.ts';
import type { CardData } from '../types/reportCardTypes';

export default function useMyLikeReportCardData(pageSize: number) {
    return useApiInfiniteQuery<CardData>(
        `/card/interaction/report/me/like`,
        {
            pageSize,
            idField: 'reportId',
        },
        {
            retry: false,
            gcTime: 24 * 60 * 60 * 1000,
            placeholderData: (prev) => prev,
        },
    );
}
