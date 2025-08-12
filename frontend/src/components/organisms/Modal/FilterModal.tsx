import ModalHeaderButtons from '../../molecules/ModalHeaderButton/ModalHeaderButton.tsx';
import FilterModalContent from '../../molecules/ModalContent/FilterModalContent.tsx';
import BaseModal from './BaseModal.tsx';

interface FilterColumn {
    title: string;
    filterLabels: string[];
}

interface propsState {
    title: string;
    description: string;
    filterColumns: FilterColumn[];
}

export default function FilterModal(props: propsState) {
    const { title, description, filterColumns } = props;
    return (
        <BaseModal
            title={title}
            description={description}
            modalHeaderChildren={modalHeaderChildren}
            footerFirstButtonText='전체 취소'
            footerSecondButtonText='필터 적용'
        >
            <FilterModalContent filterColumns={filterColumns} />
        </BaseModal>
    );
}

const modalHeaderButtonProps = {
    firstButtonText: '실시간 날씨',
    secondButtonText: '실시간 안전',
};

const modalHeaderChildren = <ModalHeaderButtons {...modalHeaderButtonProps} />;
