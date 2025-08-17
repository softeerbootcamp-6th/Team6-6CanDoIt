import { css } from '@emotion/react';
import { createPortal } from 'react-dom';
import { theme } from '../../../theme/theme';

interface ModalProps {
    children: React.ReactNode;
    onClose: () => void;
}

export default function Modal({ children, onClose }: ModalProps) {
    return createPortal(
        <div css={overlayStyles} onClick={onClose}>
            <div css={modalStyles} onClick={(e) => e.stopPropagation()}>
                <div css={contentStyles}>{children}</div>
                <button css={buttonStyles} onClick={onClose}>
                    확인
                </button>
            </div>
        </div>,
        document.body,
    );
}

const { colors, typography } = theme;

const overlayStyles = css`
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
`;

const modalStyles = css`
    background: ${colors.grey[20]};
    border-radius: 1rem;
    height: 38rem;
    width: 40rem;
    display: flex;
    flex-direction: column;
`;

const contentStyles = css`
    flex: 1 1 auto;
    padding: 2rem;
    font-size: ${typography.fontSize.body};
    color: ${colors.grey[80]};
    overflow-y: auto;
    overflow-x: hidden;
    word-break: break-word;
    overflow-wrap: break-word;

    & h2 {
        font-size: ${typography.fontSize.label};
        font-weight: ${typography.fontWeight.bold};
        margin-bottom: 1rem;
    }

    & pre {
        white-space: pre-wrap;
        word-wrap: break-word;
        line-height: 1.5;
        font-size: ${typography.fontSize.body};
    }
`;

const buttonStyles = css`
    all: unset;
    font-size: ${typography.fontSize.label};
    font-weight: 600;
    text-align: center;
    border-top: 1px solid ${colors.grey[30]};
    box-sizing: border-box;
    width: 100%;
    height: 4rem;
    padding: 1rem 0.6rem;
    border-radius: 0.5rem;
    color: ${colors.status.normal.excellent};
    cursor: pointer;
`;
