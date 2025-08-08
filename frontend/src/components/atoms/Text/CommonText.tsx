import { createTextStyle } from '../../../utils/utils.ts';
import { css } from '@emotion/react';
import type { FontSizeType, FontWeightType } from '../../../types/themeTypes';

type TextTagType = 'span' | 'p';

interface PropsState {
    TextTag: TextTagType;
    fontSize?: FontSizeType;
    fontWeight?: FontWeightType;
    color?: string;
    children: React.ReactNode;
    flexColoumn?: boolean;
}

export default function CommonText(props: PropsState) {
    const { TextTag, color, fontSize, fontWeight, children, flexColoumn } =
        props;

    const textStyle = css`
        ${flexColoumn &&
        'display: flex; flex-direction: column; align-items: center;'}
        ${createTextStyle({
            fontSize,
            fontWeight,
            color,
        })}
    `;

    return <TextTag css={textStyle}>{children}</TextTag>;
}
