import TableCell from '../../atoms/Table/TableCell';

interface Props {
    columns: string[];
}

export default function TableHeadRow({ columns }: Props) {
    return (
        <tr>
            {columns.map((col) => (
                <TableCell key={col}>{col}</TableCell>
            ))}
        </tr>
    );
}
