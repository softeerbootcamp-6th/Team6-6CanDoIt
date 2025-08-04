interface PropsState {
    textTag: textTag;
    fontSize?: string;
    fontWeight?: string;
    color?: string;
    children?: React.ReactNode;
}

type textTag = 'span' | 'p';

export default function Text({
    textTag,
    color = 'grey-100',
    fontSize = 'label',
    fontWeight = 'medium',
    children,
}: PropsState) {
    const Tag = textTag;

    return (
        <Tag
            className={`text-${color} fontSize-${fontSize} fontWeight-${fontWeight}`}
        >
            {children}
        </Tag>
    );
}
