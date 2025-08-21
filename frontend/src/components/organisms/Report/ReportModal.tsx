import BaseModal from './BaseModal.tsx';
import ReportModalContent from '../../molecules/Modal/ReportModalContent.tsx';
import type { FilterColumn } from '../../../types/FilterTypes';

interface PropsState {
    title: string;
    description: string;
    filterColumn: FilterColumn;
    isOpen?: boolean;
    onClose?: () => void;
    onSubmit?: (event: React.FormEvent<HTMLFormElement>) => void;
}

export default function ReportModal(props: PropsState) {
    const { title, description, filterColumn, isOpen, onClose, onSubmit } =
        props;

    const secondButtonClickHandler = () => {
        (
            document.getElementById('reportForm') as HTMLFormElement | null
        )?.requestSubmit();
    };

    return (
        <BaseModal
            title={title}
            description={description}
            width='63.6875rem'
            footerFirstButtonText='취소'
            footerSecondButtonText='글 등록하기'
            isOpen={isOpen}
            onClose={onClose}
            onFirstButtonClick={onClose}
            onSecondButtonClick={secondButtonClickHandler}
        >
            <form id='reportForm' onSubmit={onSubmit}>
                <ReportModalContent filterColumn={filterColumn} />
            </form>
        </BaseModal>
    );
}
