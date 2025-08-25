import LabelButtonsColumn from '../../organisms/Report/LabelButtonsColumn.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import type { FilterColumn, Keyword } from '../../../types/FilterTypes';

interface PropsState {
    filterColumn: FilterColumn;
    selectedKeywords: Record<Keyword, number[]>;
    updateSelectedKeywords: (keyword: Keyword, selectedIds: number[]) => void;
}

export default function FilterModalContent({
    filterColumn,
    selectedKeywords,
    updateSelectedKeywords,
}: PropsState) {
    return (
        <div css={filterContentStyle}>
            {(Object.keys(filterColumn) as Keyword[]).map((keyword) => (
                <LabelButtonsColumn
                    key={keyword}
                    keyword={keyword}
                    filterLabels={filterColumn[keyword]}
                    selectedIds={selectedKeywords[keyword]}
                    onSelectionChange={(selectedIds) =>
                        updateSelectedKeywords(keyword, selectedIds)
                    }
                />
            ))}
        </div>
    );
}

const { colors } = theme;

const filterContentStyle = css`
    width: 100%;
    display: flex;
    box-sizing: border-box;
    background-color: ${colors.greyOpacityWhite[98]};
    gap: 1.5rem;
    padding: 1.5rem 1.5rem 2rem;
`;
