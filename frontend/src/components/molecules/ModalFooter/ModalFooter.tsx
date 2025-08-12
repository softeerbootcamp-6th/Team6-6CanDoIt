import { css } from '@emotion/react';
import ModalButton from '../ModalButton/ModalButton.tsx';

interface propsState {
    firstButtonText: string;
    secondButtonText: string;
}

export default function ModalFooter(props: propsState) {
    const { firstButtonText, secondButtonText } = props;
    return (
        <div css={modalFooterStyle}>
            <ModalButton textColor='grey-80'>{firstButtonText}</ModalButton>
            <ModalButton>{secondButtonText}</ModalButton>
        </div>
    );
}

const modalFooterStyle = css`
    width: 100%;
    height: max-content;
    display: flex;
    align-items: center;
    padding: 1.5rem 3.8125rem;
    box-sizing: border-box;
`;
