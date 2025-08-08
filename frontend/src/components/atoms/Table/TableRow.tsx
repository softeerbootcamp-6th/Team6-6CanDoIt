interface PropsState {
    children: React.ReactNode;
}

export default function TableRow({ children }: PropsState) {
    return <tr>{children}</tr>;
}
