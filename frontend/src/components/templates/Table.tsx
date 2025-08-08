import { css } from '@emotion/react';
import TableHeadRow from '../molecules/Table/TableHeadRow';
import TableBody from '../organisms/Table/TableBody';
import { theme } from '../../theme/theme';

interface Props<T extends Record<string, React.ReactNode>> {
    columns: string[];
    data: T[];
    colWidths?: string[];
}

const { colors, typography } = theme;

export default function Table<T extends Record<string, React.ReactNode>>({
    columns,
    data,
    colWidths = [],
}: Props<T>) {
    return (
        <table css={tableStyles}>
            <colgroup>
                {columns.map((_, idx) => (
                    <col
                        key={idx}
                        css={css`
                            width: ${colWidths[idx] || 'auto'};
                        `}
                    />
                ))}
            </colgroup>

            <thead css={theadStyles}>
                <TableHeadRow columns={columns} />
            </thead>
            <TableBody data={data} />
        </table>
    );
}

const tableStyles = css`
    color: ${colors.grey[100]};
    border-collapse: separate;
    text-align: left;
    width: 87rem;

    & tbody tr:first-of-type td {
        padding-top: 2.5rem;
    }

    & tbody td {
        padding: 1.75rem 0 2.25rem 1.25rem;
        border-bottom: 0.4px solid ${colors.grey[50]};
        font-size: ${typography.fontSize.body};
        font-weight: ${typography.fontWeight.bold};
        line-height: normal;
    }
`;

const theadStyles = css`
    background-color: ${colors.grey[20]};
    color: ${colors.grey[50]};
    font-weight: ${typography.fontWeight.bold};

    & tr {
        border-radius: 0.75rem;
        & td:first-of-type {
            border-top-left-radius: 0.75rem;
            border-bottom-left-radius: 0.75rem;
        }

        & td:last-of-type {
            border-top-right-radius: 0.75rem;
            border-bottom-right-radius: 0.75rem;
        }
    }

    & td {
        padding: 1.25rem 0 1.25rem 1.25rem;
        font-size: ${typography.fontSize.body};
        font-weight: ${typography.fontWeight.bold};
    }
`;
