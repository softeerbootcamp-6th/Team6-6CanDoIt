import { theme } from '../theme/theme.ts';
import type {
    ColorCategoryType,
    ColorValueType,
    FontSizeType,
    FontWeightType,
} from '../types/themeTypes.d.ts';

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
