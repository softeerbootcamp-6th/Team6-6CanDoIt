import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    children: React.ReactNode;
}

const { colors, typography } = theme;

export default function WarningText({ children }: PropsState) {
    return <span css={textStyles}>{children}</span>;
}

const textStyles = css`
    color: ${colors.status.regular.bad};
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.regular};
    line-height: 140%;
`;
