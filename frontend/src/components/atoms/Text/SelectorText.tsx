import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    children?: string;
}

export default function SelectorText({ children }: PropsState) {
    return <span css={textStyles}>{children}</span>;
}

const { colors, typography } = theme;

const textStyles = css`
    font-family: Pretendard;
    color: ${colors.grey[80]};
    font-size: ${typography.fontSize.body};
    font-weight: ${typography.fontWeight.medium};
    text-align: center;
    line-height: 150%;
`;
