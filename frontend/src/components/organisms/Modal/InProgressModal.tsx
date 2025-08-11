import ModalHeader from '../../molecules/ModalHeader/ModalHeader.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import ModalFooter from '../../molecules/ModalFooter/ModalFooter.tsx';
import DayColumn from '../../molecules/DayColumn/DayColumn.tsx';
import BaseModal, {
    type BaseModalProps,
    modalContentBase,
} from './BaseModal.tsx';
import { css } from '@emotion/react';

type alertTime = {
    day: string;
    startTime: string;
    endTime: string;
};

interface InProgressModalProps extends BaseModalProps {
    alertTimes: alertTime[];
}

export default function InProgressModal(props: InProgressModalProps) {
    const { title, description, alertTimes } = props;
    return (
        <BaseModal>
            <ModalHeader title={title} description={description}>
                <CommonText {...textProps}>
                    종료 시간에 자동으로 알림 해제됩니다
                </CommonText>
            </ModalHeader>
            <div css={contentStyle}>
                <DayColumn title='요일' alertTimes={alertTimes} />
                <DayColumn title='시간' alertTimes={alertTimes} />
            </div>
            <ModalFooter firstButtonText='취소' secondButtonText='해제하기' />
        </BaseModal>
    );
}

const textProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'grey-60',
} as const;

const contentStyle = css`
    ${modalContentBase};
    gap: 1.5rem;
    padding: 1.5rem 1.5rem 2rem;
`;
