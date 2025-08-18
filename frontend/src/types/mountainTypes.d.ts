export interface MountainData {
    mountainId: string;
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
    courseId: string;
    courseName: string;
}
