import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    children: React.ReactNode;
}

const { colors } = theme;

export default function CautionText({ children }: PropsState) {
    return <span css={cautionTextStyles}>{children}</span>;
}

const cautionTextStyles = css`
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    background-color: ${colors.status.light.bad};
    color: ${colors.status.normal.bad};
`;
