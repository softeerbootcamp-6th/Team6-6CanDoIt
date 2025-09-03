import FilterModalContent from '../../molecules/Modal/FilterModalContent.tsx';
import BaseModal from './BaseModal.tsx';
import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import type {
    FilterColumn,
    Keyword,
    SelectedColumn,
} from '../../../types/filterTypes';
import { parseFilterFromUrl } from '../../../utils/utils.ts';

interface PropsState {
    title: string;
    description: string;
    filterColumn: FilterColumn;
    isOpen?: boolean;
    onClose?: () => void;
    anchorElement?: HTMLElement | null;
}

export default function FilterModal(props: PropsState) {
    const {
        title,
        description,
        filterColumn,
        isOpen = false,
        onClose,
        anchorElement,
    } = props;

    const [searchParams, setSearchParams] = useSearchParams();

    const [selectedKeywords, setSelectedKeywords] = useState<SelectedColumn>({
        weatherKeywords: parseFilterFromUrl(searchParams, 'weatherKeywords'),
        rainKeywords: parseFilterFromUrl(searchParams, 'rainKeywords'),
        etceteraKeywords: parseFilterFromUrl(searchParams, 'etceteraKeywords'),
    });

    const updateSelectedKeywords = (
        keyword: Keyword,
        selectedIds: number[],
    ) => {
        setSelectedKeywords({
            ...selectedKeywords,
            [keyword]: selectedIds,
        });
    };

    const applyFilters = () => {
        const urlSearchParams = new URLSearchParams(searchParams);
        keywords.forEach((keyword) => {
            const selectedIds = selectedKeywords[keyword];
            urlSearchParams.delete(keyword);
            urlSearchParams.set(keyword, `[${selectedIds.join(',')}]`);
        });

        setSearchParams(urlSearchParams);
        onClose?.();
    };

    const clearFilters = () => {
        const urlSearchParams = new URLSearchParams(searchParams);
        keywords.forEach((keyword) => urlSearchParams.delete(keyword));
        setSearchParams(urlSearchParams);
        setSelectedKeywords({
            weatherKeywords: [],
            rainKeywords: [],
            etceteraKeywords: [],
        });
        onClose?.();
    };

    return (
        <BaseModal
            title={title}
            description={description}
            footerFirstButtonText='전체 취소'
            footerSecondButtonText='필터 적용'
            isOpen={isOpen}
            onClose={onClose}
            anchorElement={anchorElement}
            onFirstButtonClick={clearFilters}
            onSecondButtonClick={applyFilters}
        >
            <FilterModalContent
                filterColumn={filterColumn}
                selectedKeywords={selectedKeywords}
                updateSelectedKeywords={updateSelectedKeywords}
            />
        </BaseModal>
    );
}

const keywords: Keyword[] = [
    'weatherKeywords',
    'rainKeywords',
    'etceteraKeywords',
];
