import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    children: React.ReactNode;
}

export default function DayButton({ children }: propsState) {
    return <button css={buttonStyle}>{children}</button>;
}

const { colors, typography } = theme;

const buttonStyle = css`
    width: 4.25rem;
    height: 3.125rem;
    border-radius: 999px;
    border: none;
    cursor: pointer;
    background-color: ${colors.grey[30]};
    text-align: center;
    color: ${colors.grey[100]};
    font-size: ${typography.fontSize.body};
    font-weight: ${typography.fontWeight.bold};
`;
