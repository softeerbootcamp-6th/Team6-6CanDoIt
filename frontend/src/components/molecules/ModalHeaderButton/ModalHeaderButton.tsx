import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    firstButtonText: string;
    secondButtonText: string;
}

export default function ModalHeaderButtons({
    firstButtonText,
    secondButtonText,
}: propsState) {
    return (
        <div css={modalHeaderButtonWrapperStyle}>
            <button css={modalHeaderButtonStyle}>{firstButtonText}</button>
            <button css={modalHeaderButtonStyle}>{secondButtonText}</button>
        </div>
    );
}

const { colors, typography } = theme;

const modalHeaderButtonWrapperStyle = css`
    width: max-content;
    height: max-content;
    display: flex;
    & > button:not(:first-child) {
        border-left: 1.5px solid ${colors.grey[60]};
        padding-left: 0.5rem;
        margin-left: 0.5rem;
    }
`;

const modalHeaderButtonStyle = css`
    width: max-content;
    height: max-content;
    border: none;
    background-color: transparent;
    padding: 0;
    cursor: pointer;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.medium};
    color: ${colors.grey[60]};
`;
