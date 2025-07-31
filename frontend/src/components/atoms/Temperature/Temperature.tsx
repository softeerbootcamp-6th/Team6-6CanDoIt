interface PropsState {
    temperature: number;
    color?: string;
    fontSize?: string;
    fontWeight?: string;
}

export default function Temperature({
    temperature,
    color = 'grey-80',
    fontSize = 'title',
    fontWeight = '700',
}: PropsState) {
    return (
        <div
            className={`${color} fontSize-${fontSize} fontWeight-${fontWeight}`}
        >
            {temperature}°
        </div>
    );
}
