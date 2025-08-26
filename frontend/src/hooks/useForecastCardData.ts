import useApiQuery from './useApiQuery';

export const useForecastCardData = (
    selectedCourseId: number,
    seletedTime: string,
) => {
    const {
        data: frontData,
        refetch: refetchFront,
        isLoading: frontCardIsLoading,
        isError: frontCardisError,
        error: frontCardError,
    } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: seletedTime },
        { retry: false, enabled: true, staleTime: 1000 * 60 * 5 },
    );

    const {
        data: backData,
        refetch: refetchBack,
        isLoading: backCardIsLoading,
        isError: backCardIsError,
        error: backCardError,
    } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}/schedule`,
        { startDateTime: seletedTime },
        { retry: false, enabled: true, staleTime: 1000 * 60 * 5 },
    );

    const isLoading = frontCardIsLoading || backCardIsLoading;
    const isError = frontCardisError || backCardIsError;
    const error = frontCardError || backCardError;

    return {
        frontCard: frontData,
        backCard: backData,
        refetch: async () => {
            await Promise.all([refetchFront(), refetchBack()]);
        },
        isLoading,
        isError,
        error,
    };
};
