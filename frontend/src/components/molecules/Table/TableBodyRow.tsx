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
            {Object.values(rowData).map((value, index) => (
                <TableCell key={index}>{value}</TableCell>
            ))}
        </TableRow>
    );
}
