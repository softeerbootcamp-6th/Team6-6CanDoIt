import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

const { colors, typography } = theme;

interface LabelTextProps {
    text: string;
}

export default function LabelText({ text }: LabelTextProps) {
    return <span css={labelTextStyles}>{text}</span>;
}

const labelTextStyles = css`
    color: ${colors.grey[100]};
    font-weight: ${typography.fontWeight.bold};
    font-size: ${typography.fontSize.caption};
`;
