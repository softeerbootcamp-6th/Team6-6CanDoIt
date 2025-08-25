import useApiQuery from './useApiQuery';

export const useForecastCardData = (
    selectedCourseId: number,
    seletedTime: string,
) => {
    const { data: frontData, refetch: refetchFront } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: seletedTime },
        { retry: false, enabled: true },
    );

    const { data: backData, refetch: refetchBack } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}/schedule`,
        { startDateTime: seletedTime },
        { retry: false, enabled: true },
    );
    return {
        frontCard: frontData,
        backCard: backData,
        refetch: async () => {
            await Promise.all([refetchFront(), refetchBack()]);
        },
    };
};
