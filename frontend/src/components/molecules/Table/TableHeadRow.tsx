import TableCell from '../../atoms/Table/TableCell';
import TableRow from '../../atoms/Table/TableRow';

interface Props {
    columns: string[];
}

export default function TableHeadRow({ columns }: Props) {
    return (
        <TableRow>
            {columns.map((col) => (
                <TableCell key={col}>{col}</TableCell>
            ))}
        </TableRow>
    );
}
