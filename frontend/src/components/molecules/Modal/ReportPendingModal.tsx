import { createPortal } from 'react-dom';
import { css, keyframes } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons.tsx';

export default function ReportPendingModal() {
    return createPortal(
        <div css={overlayStyle} role='dialog' aria-modal='true'>
            <Icon name='clear-day' width={2} height={2} color='grey-100' />
            <Icon name='rain' width={2} height={2} color='grey-100' />
            <Icon name='snow' width={2} height={2} color='grey-100' />
            <Icon name='thunderstorm' width={2} height={2} color='grey-100' />
        </div>,
        document.body,
    );
}

const show2 = keyframes`
  0%, 24.99% { opacity: 0; }
  25%, 99% { opacity: 1; }
  100% { opacity: 0; }
`;

const show3 = keyframes`
  0%, 49.99% { opacity: 0; }
  50%, 99% { opacity: 1; }
  100% { opacity: 0; }
`;

const show4 = keyframes`
  0%, 74.99% { opacity: 0; }
  75%, 99% { opacity: 1; }
  100% { opacity: 0; }
`;

const overlayStyle = css`
    position: fixed;
    inset: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 50;

    & > :nth-of-type(2) {
        animation: ${show2} 2s linear infinite;
    }
    & > :nth-of-type(3) {
        animation: ${show3} 2s linear infinite;
    }
    & > :nth-of-type(4) {
        animation: ${show4} 2s linear infinite;
    }
`;
