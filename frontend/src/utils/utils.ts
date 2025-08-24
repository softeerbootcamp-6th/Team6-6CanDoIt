import { theme } from '../theme/theme.ts';
import type {
    ColorCategoryType,
    ColorValueType,
    FontSizeType,
    FontWeightType,
} from '../types/themeTypes.d.ts';

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
