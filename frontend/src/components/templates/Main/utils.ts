import type {
    MountainCourse,
    MountainData,
} from '../../../types/mountainTypes';
import { convertToIconName } from '../../../utils/utils.ts';

export function refactorMountainsData(data: MountainData[]) {
    return data.map((mountain) => ({
        mountainId: mountain.mountainId,
        mountainName: mountain.mountainName,
        mountainDescription: mountain.mountainDescription,
        weatherIconName: convertToIconName({
            precipitationType: mountain.weatherMetric.precipitationType,
            sky: mountain.weatherMetric.sky,
        }),
        surfaceTemperature: mountain.weatherMetric.surfaceTemperature,
        summitTemperature: mountain.weatherMetric.topTemperature,
    }));
}

interface Option {
    id: string;
    name: string;
}
export function refactorMountainDataToOptions(
    mountainsData: MountainData[],
): Option[] {
    return mountainsData.map((mountain) => ({
        id: mountain.mountainId,
        name: mountain.mountainName,
    }));
}

export function refactorCoursesDataToOptions(
    coursesData: MountainCourse[],
): Option[] {
    return coursesData.map((course) => ({
        id: course.courseId,
        name: course.courseName,
    }));
}

export function validate(values: {
    mountainId: string;
    courseId?: string;
    weekdayId?: string;
}) {
    const { mountainId, courseId = '0', weekdayId = '0' } = values;
    if (mountainId === '0') return '산을 선택해주세요';
    if (courseId === '0') return '코스를 선택해주세요';
    if (weekdayId === '0') return '요일을 선택해주세요';
    return null;
}
