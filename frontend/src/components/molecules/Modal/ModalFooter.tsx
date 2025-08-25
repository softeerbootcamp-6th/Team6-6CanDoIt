import { css } from '@emotion/react';
import ModalButton from '../../atoms/Button/ModalButton.tsx';

interface PropsState {
    firstButtonText: string;
    secondButtonText: string;
    onFirstButtonClick?: () => void;
    onSecondButtonClick?: () => void;
}

export default function ModalFooter(props: PropsState) {
    const {
        firstButtonText,
        secondButtonText,
        onFirstButtonClick,
        onSecondButtonClick,
    } = props;
    return (
        <div css={modalFooterStyle}>
            <ModalButton onClick={onFirstButtonClick} textColor='grey-80'>
                {firstButtonText}
            </ModalButton>
            <ModalButton onClick={onSecondButtonClick}>
                {secondButtonText}
            </ModalButton>
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
