import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

export interface BaseModalProps {
    title: string;
    description: string;
}

export default function BaseModal({ children }: { children: React.ReactNode }) {
    return <div css={modalStyle}>{children}</div>;
}

const { colors } = theme;

const modalStyle = css`
    width: 37.1875rem;
    height: max-content;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: ${colors.grey[30]};
    border-radius: 2.5rem;
`;

export const modalContentBase = css`
    width: 100%;
    display: flex;
    box-sizing: border-box;
    background-color: ${colors.greyOpacityWhite[98]};
`;
