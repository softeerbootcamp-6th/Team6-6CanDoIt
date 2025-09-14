import { theme } from '../theme/theme.ts';
import type {
    ColorCategoryType,
    ColorValueType,
    FontSizeType,
    FontWeightType,
} from '../types/themeTypes.d.ts';
import type { MountainCourse, MountainData } from '../types/mountainTypes';
import type { Option } from '../types/searchBarTypes';

type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';

export function getColor({
    colors,
    colorString,
}: {
    colors: typeof theme.colors;
    colorString: string;
}) {
    const [category, value] = colorString.split('-');
    return colors[category as ColorCategoryType][value as ColorValueType];
}

export function createTextStyle({
    fontSize = 'label',
    fontWeight = 'regular',
    color = 'grey-100',
    lineHeight = 1,
}: {
    fontSize?: FontSizeType;
    fontWeight?: FontWeightType;
    color?: string;
    lineHeight?: number;
}) {
    return `
        color: ${getColor({ colors: theme.colors, colorString: color })};
        font-size: ${theme.typography.fontSize[fontSize]};
        font-weight: ${theme.typography.fontWeight[fontWeight]};
        line-height: ${lineHeight};
    `;
}

export function convertToIconName({
    precipitationType,
    sky,
}: {
    precipitationType: string;
    sky: string;
}) {
    if (precipitationType === 'NONE') {
        if (sky === 'SUNNY') return 'clear-day';
        if (sky === 'CLOUDY') return 'partly-cloudy-day';
        if (sky === 'OVERCAST') return 'cloudy';
    }
    if (precipitationType === 'RAIN' || precipitationType === 'SLEET')
        return 'rain';
    if (precipitationType === 'SNOW') return 'snow';
    if (precipitationType === 'SHOWER') return 'shower-rain';
    if (precipitationType === 'DRIZZLE' || precipitationType === 'DRIZZLE_SNOW')
        return 'occasional-rain';
    if (precipitationType === 'SNOW_FLURRY') return 'occasional-snow';
    return '';
}
export function parseDuration(totalDuration: number): {
    hour: number;
    min: number;
} {
    const hour = Math.floor(totalDuration);
    const min = Math.round((totalDuration - hour) * 60);
    return { hour, min };
}

export function formatDateToKorean(dateStr: string): string {
    const date = new Date(dateStr);
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 0~11이므로 +1
    const day = String(date.getDate()).padStart(2, '0');
    return `${month}월 ${day}일`;
}

export const covertToWeatherByIconName = (iconName: string): Background => {
    switch (iconName) {
        case 'clear-day':
            return 'sunny';
        case 'partly-cloudy-day':
        case 'cloudy':
            return 'cloudy';
        case 'snow':
        case 'occasional-snow':
            return 'snow';
        case 'shower-rain':
        case 'occasional-rain':
            return 'rain';
        default:
            return 'sunny';
    }
};

export const convertWeatherToKorean = (weather: Background): string => {
    switch (weather) {
        case 'sunny':
            return '맑음';
        case 'cloudy':
            return '구름';
        case 'snow':
            return '눈';
        case 'rain':
            return '비';
        default:
            return '맑음';
    }
};

export const validateAccessToken = () => {
    const accessToken =
        localStorage.getItem('accessToken') ??
        sessionStorage.getItem('accessToken');

    if (!accessToken) return false;

    const payload = JSON.parse(atob(accessToken.split('.')[1]));
    const currentTime = Math.floor(Date.now() / 1000);

    if (payload.exp < currentTime) {
        localStorage.removeItem('accessToken');
        sessionStorage.removeItem('accessToken');
        return false;
    }

    return true;
};

export async function convertToWebp(
    file: File,
    quality = 0.8,
): Promise<{ imageFile: File; previewUrl: string }> {
    const bitmap = await createImageBitmap(file);

    let blob: Blob;
    if (typeof OffscreenCanvas !== 'undefined') {
        const canvas = new OffscreenCanvas(bitmap.width, bitmap.height);
        const ctx = canvas.getContext('2d');
        if (!ctx) throw new Error('2D context not available');
        ctx.drawImage(bitmap, 0, 0);
        blob = await canvas.convertToBlob({ type: 'image/webp', quality });
    } else {
        const canvas = document.createElement('canvas');
        canvas.width = bitmap.width;
        canvas.height = bitmap.height;
        const ctx = canvas.getContext('2d');
        if (!ctx) throw new Error('2D context not available');
        ctx.drawImage(bitmap, 0, 0);
        blob = await new Promise<Blob>((resolve, reject) =>
            canvas.toBlob(
                (b) => (b ? resolve(b) : reject(new Error('toBlob failed'))),
                'image/webp',
                quality,
            ),
        );
    }

    const webpFile = new File([blob], file.name.replace(/\.[^.]+$/, '.webp'), {
        type: 'image/webp',
    });
    const previewUrl = URL.createObjectURL(webpFile);
    return { imageFile: webpFile, previewUrl };
}

export const parseFilterFromUrl = (
    searchParams: URLSearchParams,
    key: string,
): number[] => {
    const paramValue = searchParams.get(key);
    if (!paramValue) return [];

    try {
        return paramValue
            .replace(/[\[\]]/g, '')
            .split(',')
            .filter((id) => id !== '')
            .map((id) => Number(id));
    } catch (e) {
        console.error(`Failed to parse filter ${key}:`, e);
        return [];
    }
};

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

export function getCurrentTime(): Date {
    return new Date();
}

export function formatTimeDifference({
    pastISO,
    nowDate = null,
}: {
    pastISO: string;
    nowDate?: Date | null;
}): string {
    const now = nowDate ?? getCurrentTime();
    const past = new Date(pastISO);
    const diffMs = now.getTime() - past.getTime();

    if (diffMs < 0) return '';

    const diffSec = Math.floor(diffMs / 1000);
    const diffMin = Math.floor(diffSec / 60);
    const diffHour = Math.floor(diffMin / 60);
    const diffDay = Math.floor(diffHour / 24);

    if (diffMin < 60) {
        return `${diffMin}분 전`;
    } else if (diffHour < 24) {
        return `${diffHour}시간 전`;
    } else {
        return `${diffDay}일 전`;
    }
}

export function filterGatherer({
    weatherKeywords = [],
    rainKeywords = [],
    etceteraKeywords = [],
}: {
    weatherKeywords?: string[];
    rainKeywords?: string[];
    etceteraKeywords?: string[];
}) {
    return [...weatherKeywords, ...rainKeywords, ...etceteraKeywords];
}
