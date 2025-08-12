import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import ModalHeader from '../../molecules/ModalHeader/ModalHeader.tsx';
import ModalFooter from '../../molecules/ModalFooter/ModalFooter.tsx';

interface propsState {
    title: string;
    description: string;
    modalHeaderChildren?: React.ReactNode;
    footerFirstButtonText: string;
    footerSecondButtonText: string;
    children: React.ReactNode;
}

export default function BaseModal(props: propsState) {
    const {
        title,
        description,
        modalHeaderChildren,
        footerFirstButtonText,
        footerSecondButtonText,
        children,
    } = props;
    return (
        <div css={modalStyle}>
            <ModalHeader title={title} description={description}>
                {modalHeaderChildren}
            </ModalHeader>
            {children}
            <ModalFooter
                firstButtonText={footerFirstButtonText}
                secondButtonText={footerSecondButtonText}
            />
        </div>
    );
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
