import type { ReactNode } from 'react';

interface PropsState {
    headingTag: HeadingTag;
    fontSize?: string;
    fontWeight?: string;
    color?: string;
    children?: string | ReactNode;
}

type HeadingTag = 'h1' | 'h2' | 'h3' | 'h4' | 'h5' | 'h6';

export default function Heading({
    headingTag,
    color = 'grey-100',
    fontSize = 'display',
    fontWeight = 'bold',
    children,
}: PropsState) {
    const Tag = headingTag;

    return (
        <Tag
            className={`text-${color} fontSize-${fontSize} fontWeight-${fontWeight}`}
        >
            {children}
        </Tag>
    );
}
