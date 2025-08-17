export interface MountainData {
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

export interface SelectedMountainData {
    mountainName: string;
    mountainDescription: string;
    course?: string;
    timeToGo?: string;
}

export interface MountainCourse {
    courseId: string;
    courseName: string;
}
