import LabelButtonsColumn from '../LabelButtonsColumn/LabelButtonsColumn.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface FilterColumn {
    title: string;
    filterLabels: string[];
}

interface propsState {
    filterColumns: FilterColumn[];
}

export default function FilterModalContent({ filterColumns }: propsState) {
    return (
        <div css={filterContentStyle}>
            {filterColumns?.map((column) => (
                <LabelButtonsColumn
                    title={column.title}
                    filterLabels={column.filterLabels}
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
