import ModalHeader from '../../molecules/ModalHeader/ModalHeader.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import DaySelector from '../../molecules/DaySelector/DaySelector.tsx';
import ModalFooter from '../../molecules/ModalFooter/ModalFooter.tsx';
import BaseModal, {
    type BaseModalProps,
    modalContentBase,
} from './BaseModal.tsx';
import ModalHeaderButtons from '../../molecules/ModalHeaderButton/ModalHeaderButton.tsx';
import { css } from '@emotion/react';
import TimeRow from '../../molecules/TimeRow/TimeRow.tsx';

export default function AlertModal(props: BaseModalProps) {
    const { title, description } = props;
    return (
        <BaseModal>
            <ModalHeader title={title} description={description}>
                <ModalHeaderButtons {...modalHeaderButtonProps} />
            </ModalHeader>
            <div css={alertContentStyle}>
                <CommonText {...sectionTitleTextProps}>날짜</CommonText>
                <DaySelector />
                <CommonText {...sectionTitleTextProps}>시간</CommonText>

                <TimeRow label='시작' day='오늘' />
                <TimeRow label='종료' day='내일' />
            </div>
            <ModalFooter {...modalFooterProps} />
        </BaseModal>
    );
}

const modalHeaderButtonProps = {
    firstButtonText: '시작 시간에서 알림 시작',
    secondButtonText: '종료 시간에서 알림 종료',
} as const;

const modalFooterProps = {
    firstButtonText: '취소',
    secondButtonText: '알림 받기',
} as const;

const sectionTitleTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'greyOpacityWhite-40',
} as const;

const alertContentStyle = css`
    ${modalContentBase};
    flex-direction: column;
    gap: 1rem;
    padding: 1.75rem 1.5rem;
`;
