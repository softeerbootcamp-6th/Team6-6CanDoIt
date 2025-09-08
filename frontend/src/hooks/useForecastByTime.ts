import { useMemo } from 'react';
import useApiQuery from './useApiQuery.ts';
import { getCurrentHourString } from '../components/templates/Forecast/helpers.ts';

export interface ForecastItem {
    dateTime: string;
    temperature: number;
    precipitationType: string;
    sky: string;
}

interface UseMountainForecastProps {
    mountainId: number;
}

export function useForecastByTime({ mountainId }: UseMountainForecastProps) {
    const currentTimeStr = getCurrentHourString();

    const {
        data: rawData,
        isLoading,
        error,
    } = useApiQuery<ForecastItem[]>(
        `/card/mountain/${mountainId}/forecast`,
        { startDateTime: currentTimeStr },
        { retry: false },
    );

    const forecastData = useMemo(() => {
        if (!rawData) return [];
        const emptyItems: ForecastItem[] = Array.from({ length: 14 }, () => ({
            dateTime: '',
            temperature: 0,
            precipitationType: '',
            sky: '',
        }));
        return [...rawData, ...emptyItems];
    }, [rawData]);

    return {
        forecastData,
        rawDataLength: rawData?.length ?? 0,
        isLoading,
        error,
    };
}
