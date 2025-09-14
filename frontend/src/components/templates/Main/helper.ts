import type { MountainData } from '../../../types/mountainTypes';
import { convertToIconName } from '../../../utils/utils.ts';

export function refactorMountainsData(data: MountainData[]) {
    return data.map((mountain) => ({
        mountainId: mountain.mountainId,
        mountainName: mountain.mountainName,
        mountainImageUrl: mountain.mountainImageUrl,
        mountainDescription: mountain.mountainDescription,
        weatherIconName: convertToIconName({
            precipitationType: mountain.weatherMetric.precipitationType,
            sky: mountain.weatherMetric.sky,
        }),
        surfaceTemperature: mountain.weatherMetric.surfaceTemperature,
        summitTemperature: mountain.weatherMetric.topTemperature,
    }));
}

export function validate(values: {
    mountainId: number;
    courseId?: number;
    weekdayId?: number;
}) {
    const { mountainId, courseId = 0, weekdayId = 0 } = values;
    if (mountainId === 0) return '산을 선택해주세요';
    if (courseId === 0) return '코스를 선택해주세요';
    if (weekdayId === 0) return '요일을 선택해주세요';
    return null;
}
