import { backCard, frontCard } from '../constants/placeholderData';
import useApiQuery from './useApiQuery';

export const useForecastCardData = (
    selectedCourseId: number,
    seletedTime: string,
) => {
    const { data: frontData, refetch: refetchFront } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: seletedTime },
        { retry: false, enabled: false, placeholderData: frontCard },
    );

    const { data: backData, refetch: refetchBack } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}/schedule`,
        { startDateTime: seletedTime },
        { retry: false, enabled: false, placeholderData: backCard },
    );

    return {
        frontCard: frontData,
        backCard: backData,
        refetch: async () => {
            await Promise.all([refetchFront(), refetchBack()]);
        },
    };
};
