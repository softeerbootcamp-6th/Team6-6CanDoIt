import TableCell from '../../atoms/Table/TableCell';
import TableRow from '../../atoms/Table/TableRow';

interface Props<T extends Record<string, React.ReactNode>> {
    rowData: T;
}

export default function TableBodyRow<
    T extends Record<string, React.ReactNode>,
>({ rowData }: Props<T>) {
    return (
        <TableRow>
            {Object.entries(rowData).map(([k, value]) => (
                <TableCell key={k}>{value}</TableCell>
            ))}
        </TableRow>
    );
}
