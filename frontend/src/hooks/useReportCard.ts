import useApiInfiniteQuery from './useApiInfiniteQuery.ts';
import type { CardData } from '../types/reportCardTypes';

interface reportData {
    courseId: number;
    reportType: 'WEATHER' | 'SAFE';
    weatherKeywords: number[];
    rainKeywords: number[];
    etceteraKeywords: number[];
    pageSize: number;
}

export default function useReportCardData({
    courseId,
    reportType,
    weatherKeywords,
    rainKeywords,
    etceteraKeywords,
    pageSize,
}: reportData) {
    return useApiInfiniteQuery<CardData>(
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
            gcTime: 24 * 60 * 60 * 1000,
            placeholderData: (prev) => prev,
        },
    );
}
