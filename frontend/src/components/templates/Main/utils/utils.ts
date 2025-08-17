import type {
    MountainCourse,
    MountainData,
    SelectedMountainData,
} from '../../../../types/mountainTypes';
import { convertToIconName } from '../../../../utils/utils.ts';

export function refactorMountainsData(data: MountainData[]) {
    return data.map((mountain) => ({
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
    return mountainsData.map((mountain, index) => ({
        id: index.toString(),
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
    mountain: Option;
    course?: Option;
    weekday?: Option;
}) {
    if (values.mountain.id === '0') return '산을 선택해주세요';
    if (values.course?.id === '0') return '코스를 선택해주세요';
    if (values.weekday?.id === '0') return '요일을 선택해주세요';
    return null;
}

export function createHandleSubmit({
    mountainsData,
    onSearchClick,
    onFormValidChange,
}: {
    mountainsData: MountainData[];
    onSearchClick: (data: SelectedMountainData) => void;
    onFormValidChange: (isValid: boolean) => void;
}) {
    return (values: {
        mountain: Option;
        course?: Option;
        weekday?: Option;
    }) => {
        const error = validate(values);
        if (error) {
            alert(error);
            onFormValidChange(false);
            return;
        }

        onFormValidChange(true);

        const mountain = mountainsData.find(
            (mountain) => mountain.mountainName === values.mountain.name,
        );
        if (!mountain) return;

        const payload: SelectedMountainData = {
            mountainName: mountain.mountainName,
            mountainDescription: mountain.mountainDescription,
            course: values.course?.name,
            timeToGo: values.weekday?.name,
        };

        onSearchClick(payload);
    };
}
