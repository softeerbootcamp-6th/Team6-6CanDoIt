import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    children?: string;
}

export default function SearchBarText({ children }: PropsState) {
    return <span css={textStyles}>{children}</span>;
}

const { colors, typography } = theme;

const textStyles = css`
    color: ${colors.grey[100]};
    font-size: ${typography.fontSize.label};
    font-weight: ${typography.fontWeight.bold};
    font-style: normal;
    line-height: normal;
`;
