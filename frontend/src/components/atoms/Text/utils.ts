import { theme } from '../../../theme/theme.ts';
import { css } from '@emotion/react';

type ColorCategoryType = keyof typeof theme.colors;
type ColorValueType = keyof (typeof theme.colors)[ColorCategoryType];
type FontSizeType = keyof typeof theme.typography.fontSize;
type FontWeightType = keyof typeof theme.typography.fontWeight;

function parseColor({
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
}: {
    fontSize?: FontSizeType;
    fontWeight?: FontWeightType;
    color?: string;
}) {
    return css`
        color: ${parseColor({ colors: theme.colors, colorString: color })};
        font-size: ${theme.typography.fontSize[fontSize]};
        font-weight: ${theme.typography.fontWeight[fontWeight]};
    `;
}
