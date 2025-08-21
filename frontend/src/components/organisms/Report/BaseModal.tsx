import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import ModalHeader from '../../molecules/Modal/ModalHeader.tsx';
import ModalFooter from '../../molecules/Modal/ModalFooter.tsx';
import { createPortal } from 'react-dom';

interface PropsState {
    title: string;
    description: string;
    width?: string;
    modalHeaderChildren?: React.ReactNode;
    footerFirstButtonText: string;
    footerSecondButtonText: string;
    onFirstButtonClick?: () => void;
    onSecondButtonClick?: () => void;
    children: React.ReactNode;
    isOpen?: boolean;
    onClose?: () => void;
    anchorElement?: HTMLElement | null;
}

export default function BaseModal(props: PropsState) {
    const {
        title,
        description,
        modalHeaderChildren,
        width = '37.1875rem',
        footerFirstButtonText,
        footerSecondButtonText,
        onFirstButtonClick,
        onSecondButtonClick,
        children,
        isOpen = false,
        onClose,
        anchorElement,
    } = props;

    if (!isOpen) return null;

    return createPortal(
        <div css={overlayStyle(anchorElement)} onClick={onClose}>
            <div
                css={[
                    modalStyle({ width }),
                    anchorElement ? positionedStyle(anchorElement) : {},
                ]}
                onClick={(e) => e.stopPropagation()}
            >
                <ModalHeader title={title} description={description}>
                    {modalHeaderChildren}
                </ModalHeader>
                {children}
                <ModalFooter
                    firstButtonText={footerFirstButtonText}
                    secondButtonText={footerSecondButtonText}
                    onFirstButtonClick={onFirstButtonClick}
                    onSecondButtonClick={onSecondButtonClick}
                />
            </div>
        </div>,
        document.body,
    );
}

const { colors } = theme;

const overlayStyle = (anchorElement?: HTMLElement | null) =>
    css({
        position: 'fixed',
        inset: 0,
        zIndex: 10,
        backgroundColor: anchorElement ? undefined : colors.greyOpacity[10],
    });

const modalStyle = ({ width }: { width: string }) => css`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: ${width};
    height: max-content;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: ${colors.grey[30]};
    border-radius: 2.5rem;
    z-index: 20;
`;

const positionedStyle = (element: HTMLElement) => {
    const rect = element.getBoundingClientRect();
    return css`
        top: ${rect.bottom + 50}px;
        left: ${rect.right}px;
        transform: translateX(-98%);
    `;
};
