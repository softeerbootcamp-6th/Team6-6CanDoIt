import { css } from '@emotion/react';
import TableBodyRow from '../../molecules/Table/TableBodyRow';
import { theme } from '../../../theme/theme';

interface Props<T extends Record<string, React.ReactNode>> {
    data: T[];
}

const { typography, colors } = theme;

export default function TableBody<T extends Record<string, React.ReactNode>>({
    data,
}: Props<T>) {
    return (
        <tbody css={tbodyStyles}>
            {data.map((row, idx) => (
                <TableBodyRow key={idx} rowData={row} />
            ))}
        </tbody>
    );
}

const tbodyStyles = css`
    margin-top: 4rem;

    & td:nth-of-type(1) {
        font-weight: ${typography.fontWeight.medium};
        color: ${colors.grey[80]};
    }

    & td:nth-of-type(2) {
        color: ${colors.grey[90]};
    }

    & td:nth-of-type(3) {
        font-weight: ${typography.fontWeight.medium};
        color: ${colors.grey[80]};
    }

    & td:nth-of-type(5) {
        font-weight: ${typography.fontWeight.medium};
        color: ${colors.grey[80]};
    }
`;
