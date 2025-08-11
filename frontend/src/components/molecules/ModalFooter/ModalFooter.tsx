import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';

interface propsState {
    firstButtonText: string;
    secondButtonText: string;
}

export default function ModalFooter(props: propsState) {
    const { firstButtonText, secondButtonText } = props;
    return (
        <div css={modalFooterStyle}>
            <button css={modalButtonStyle}>
                <CommonText {...modalButtonTextProps} color='grey-80'>
                    {firstButtonText}
                </CommonText>
            </button>
            <button css={modalButtonStyle}>
                <CommonText {...modalButtonTextProps} color='grey-100'>
                    {secondButtonText}
                </CommonText>
            </button>
        </div>
    );
}

const modalButtonTextProps = {
    TextTag: 'span',
    fontSize: 'label',
    fontWeight: 'bold',
} as const;

const modalFooterStyle = css`
    width: 100%;
    height: max-content;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 1.5rem 3.8125rem;
    box-sizing: border-box;
`;

const modalButtonStyle = css`
    background: none;
    border: none;
    width: 11rem;
    height: 2.25rem;
`;
