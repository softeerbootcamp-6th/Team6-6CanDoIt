import TableCell from '../../atoms/Table/TableCell';

interface Props<T extends Record<string, React.ReactNode>> {
    rowData: T;
}

export default function TableBodyRow<
    T extends Record<string, React.ReactNode>,
>({ rowData }: Props<T>) {
    return (
        <tr>
            {Object.entries(rowData).map(([k, value]) => (
                <TableCell key={k}>{value}</TableCell>
            ))}
        </tr>
    );
}
