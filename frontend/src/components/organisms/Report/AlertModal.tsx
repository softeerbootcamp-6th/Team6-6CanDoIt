import BaseModal from './BaseModal.tsx';
import AlertModalContent from '../../molecules/Modal/AlertModalContent.tsx';
import ModalHeaderButtons from '../../molecules/Modal/ModalHeaderButton.tsx';

interface propsState {
    title: string;
    description: string;
}

export default function AlertModal(props: propsState) {
    const { title, description } = props;
    return (
        <BaseModal
            title={title}
            description={description}
            modalHeaderChildren={modalHeaderChildren}
            footerFirstButtonText='취소'
            footerSecondButtonText='알림 받기'
        >
            <AlertModalContent />
        </BaseModal>
    );
}

const modalHeaderButtonProps = {
    firstButtonText: '시작 시간에서 알림 시작',
    secondButtonText: '종료 시간에서 알림 종료',
} as const;

const modalHeaderChildren = <ModalHeaderButtons {...modalHeaderButtonProps} />;
