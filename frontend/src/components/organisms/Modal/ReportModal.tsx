import BaseModal from './BaseModal.tsx';
import ReportModalContent from '../../molecules/ModalContent/ReportModalContent.tsx';

interface FilterColumn {
    title: string;
    filterLabels: string[];
}

interface propsState {
    title: string;
    description: string;
    filterColumns: FilterColumn[];
}

export default function ReportModal(props: propsState) {
    const { title, description, filterColumns } = props;

    return (
        <BaseModal
            title={title}
            description={description}
            width='63.6875rem'
            footerFirstButtonText='취소'
            footerSecondButtonText='글 등록하기'
        >
            <ReportModalContent filterColumns={filterColumns} />
        </BaseModal>
    );
}
