import { keepPreviousData } from '@tanstack/react-query';
import useApiQuery from './useApiQuery';

interface MountainCourseData {
    courseImageUrl: string;
    duration: number;
    distance: number;
    sunrise: string;
    sunset: string;
    hikingActivityStatus: HikingActivityStatus;
}

type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';

export default function useSummaryInfo(courseId: number, dateTime: string) {
    const { data, isLoading, isError } = useApiQuery<MountainCourseData>(
        `/card/mountain/course/${courseId}`,
        { dateTime: dateTime },
        {
            placeholderData: keepPreviousData,
            retry: 3,
            enabled: !!courseId,
        },
    );

    return { data, isLoading, isError };
}
