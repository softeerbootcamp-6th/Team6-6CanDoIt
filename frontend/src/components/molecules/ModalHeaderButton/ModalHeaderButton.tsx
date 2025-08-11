import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';

export default function ModalHeaderButtons({
    firstButtonText,
    secondButtonText,
}: {
    firstButtonText: string;
    secondButtonText: string;
}) {
    return (
        <div css={modalHeaderButtonsStyle}>
            <button css={modalHeaderButtonStyle}>
                <CommonText {...textProps}>{firstButtonText}</CommonText>
            </button>
            <CommonText {...textProps}>|</CommonText>
            <button css={modalHeaderButtonStyle}>
                <CommonText {...textProps}>{secondButtonText}</CommonText>
            </button>
        </div>
    );
}

const textProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'grey-60',
} as const;

const modalHeaderButtonsStyle = css`
    width: max-content;
    height: max-content;
    display: flex;
    gap: 0.5rem;
`;

const modalHeaderButtonStyle = css`
    width: max-content;
    height: max-content;
    border: none;
    background-color: transparent;
    padding: 0;
    cursor: pointer;
`;
