import CommonText from '../../atoms/Text/CommonText.tsx';
import BaseModal from './BaseModal.tsx';
import InProgressModalContent from '../../molecules/Modal/InProgressModalContent.tsx';

interface alertTime {
    day: string;
    startTime: string;
    endTime: string;
}

interface InProgressModalProps {
    title: string;
    description: string;
    alertTime: alertTime;
}

export default function InProgressModal(props: InProgressModalProps) {
    const { title, description, alertTime } = props;
    return (
        <BaseModal
            title={title}
            description={description}
            footerFirstButtonText='취소'
            footerSecondButtonText='해제하기'
            modalHeaderChildren={modalHeaderChildren}
        >
            <InProgressModalContent alertTime={alertTime} />
        </BaseModal>
    );
}

const textProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'grey-60',
} as const;

const modalHeaderChildren = (
    <CommonText {...textProps}>종료 시간에 자동으로 알림 해제됩니다</CommonText>
);
