import CommonText from '../../atoms/Text/CommonText.tsx';
import FilterLabelButton from '../../atoms/Button/FilterLabelButton.tsx';
import { css } from '@emotion/react';
import { keywordToTitle } from './utils.ts';
import type { Filter, Keyword } from '../../../types/filterTypes';

interface PropsState {
    keyword: Keyword;
    filterLabels: Filter[];
    selectedIds: number[];
    onSelectionChange: (selectedIds: number[]) => void;
}

export default function LabelButtonsColumn(props: PropsState) {
    const { keyword, filterLabels, selectedIds, onSelectionChange } = props;
    const title = keywordToTitle(keyword);

    const buttonClickHandler = (id: number) => {
        const newSelectedIds = selectedIds.includes(id)
            ? selectedIds.filter((selectedId) => selectedId !== id)
            : [...selectedIds, id];

        onSelectionChange(newSelectedIds);
    };

    return (
        <div css={columnStyle}>
            <CommonText {...textProps}>{title}</CommonText>
            <ul css={listStyle}>
                {filterLabels.map((label) => (
                    <li key={label.id}>
                        <FilterLabelButton
                            isSelected={selectedIds.includes(label.id)}
                            onClick={() => buttonClickHandler(label.id)}
                        >
                            {label.description}
                        </FilterLabelButton>
                    </li>
                ))}
            </ul>
        </div>
    );
}

const textProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'grey-90',
} as const;

const columnStyle = css`
    display: flex;
    flex-direction: column;
    justify-content: start;
    width: max-content;
    height: max-content;
    gap: 1rem;
`;

const listStyle = css`
    width: max-content;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
`;
