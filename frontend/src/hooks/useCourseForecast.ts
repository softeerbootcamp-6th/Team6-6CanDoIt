import { keepPreviousData } from '@tanstack/react-query';
import useApiQuery from './useApiQuery';

interface CourseForecast {
    startCard: CardData;
    arrivalCard: CardData;
    adjustedArrivalCard: CardData;
    descentCard: CardData;
    courseAltitude: number;
    recommendComment: string;
    adjustedRecommendComment: string;
}

interface CardData {
    dateTime: string;
    hikingActivity: HikingActivityStatus;
    temperature: number;
    apparentTemperature: number;
    temperatureDescription: string;
    precipitation: string;
    probabilityDescription: string;
    precipitationType: string;
    sky: string;
    skyDescription: string;
    windSpeed: number;
    windSpeedDescription: string;
    humidity: number;
    humidityDescription: string;
    highestTemperature: number;
    lowestTemperature: number;
    title?: string;
}

type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';

export default function useCourseForecast(
    courseId: number,
    startDateTime: string,
) {
    return useApiQuery<CourseForecast>(
        `/card/course/${courseId}/forecast`,
        { startDateTime },
        {
            placeholderData: keepPreviousData,
            retry: 3,
            enabled: !!courseId,
        },
    );
}
