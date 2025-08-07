import { theme } from '../theme/theme.ts';

export type ColorCategoryType = keyof typeof theme.colors;
export type ColorValueType = keyof (typeof theme.colors)[ColorCategoryType];
export type FontSizeType = keyof typeof theme.typography.fontSize;
export type FontWeightType = keyof typeof theme.typography.fontWeight;
