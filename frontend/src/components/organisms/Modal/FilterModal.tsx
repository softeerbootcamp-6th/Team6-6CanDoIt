import ModalHeader from '../../molecules/ModalHeader/ModalHeader.tsx';
import LabelButtonsColumn from '../../molecules/LabelButtonsColumn/LabelButtonsColumn.tsx';
import ModalFooter from '../../molecules/ModalFooter/ModalFooter.tsx';
import BaseModal, {
    type BaseModalProps,
    modalContentBase,
} from './BaseModal.tsx';
import { css } from '@emotion/react';
import ModalHeaderButtons from '../../molecules/ModalHeaderButton/ModalHeaderButton.tsx';

type FilterColumn = {
    title: string;
    filterLabels: string[];
};

interface FilterModalProps extends BaseModalProps {
    filterColumns: FilterColumn[];
}

export default function FilterModal(props: FilterModalProps) {
    const { title, description, filterColumns } = props;
    return (
        <BaseModal>
            <ModalHeader title={title} description={description}>
                <ModalHeaderButtons {...modalHeaderButtonProps} />
            </ModalHeader>
            <div css={filterContentStyle}>
                {filterColumns?.map((column) => (
                    <LabelButtonsColumn
                        title={column.title}
                        filterLabels={column.filterLabels}
                    />
                ))}
            </div>
            <ModalFooter {...modalFooterProps} />
        </BaseModal>
    );
}

const modalHeaderButtonProps = {
    firstButtonText: '실시간 날씨',
    secondButtonText: '실시간 안전',
};

const modalFooterProps = {
    firstButtonText: '전체 취소',
    secondButtonText: '필터 적용',
};

const filterContentStyle = css`
    ${modalContentBase};
    gap: 1.5rem;
    padding: 1.5rem 1.5rem 2rem;
`;
