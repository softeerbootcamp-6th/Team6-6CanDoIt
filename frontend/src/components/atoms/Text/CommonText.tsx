import { theme } from '../../../theme/theme.ts';
import { createTextStyle } from './utils.ts';

type TextTagType = 'span' | 'p';
type FontSizeType = keyof typeof theme.typography.fontSize;
type FontWeightType = keyof typeof theme.typography.fontWeight;

interface PropsState {
    TextTag: TextTagType;
    fontSize?: string;
    fontWeight?: string;
    color?: string;
    children: React.ReactNode;
}

export default function CommonText(props: PropsState) {
    const { TextTag, color, fontSize, fontWeight, children } = props;

    const textStyle = createTextStyle({
        fontSize: fontSize as FontSizeType,
        fontWeight: fontWeight as FontWeightType,
        color,
    });

    return <TextTag css={textStyle}>{children}</TextTag>;
}
