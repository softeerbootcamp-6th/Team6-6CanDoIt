export interface MountainData {
    mountainId: number;
    mountainName: string;
    mountainImageUrl: string;
    mountainDescription: string;
    weatherMetric: {
        precipitationType: string;
        sky: string;
        surfaceTemperature: number;
        topTemperature: number;
    };
}

export interface MountainCourse {
    courseId: number;
    courseName: string;
}
