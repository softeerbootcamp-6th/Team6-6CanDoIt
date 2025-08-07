import type { ReactNode } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

type FontSizeType = keyof typeof theme.typography.fontSize;
type HeadingTagType = 'h1' | 'h2' | 'h3' | 'h4' | 'h5' | 'h6';

interface PropsState {
    HeadingTag: HeadingTagType;
    fontSize?: FontSizeType;
    children: string | ReactNode;
}

function Heading(props: PropsState) {
    const { HeadingTag, fontSize, children } = props;
    return (
        <HeadingTag css={createHeadingStyle(fontSize!)}>{children}</HeadingTag>
    );
}

export function DisplayHeading(props: PropsState) {
    const { HeadingTag, children } = props;
    return (
        <Heading
            HeadingTag={HeadingTag}
            fontSize='display'
            children={children}
        />
    );
}

export function HeadlineHeading(props: PropsState) {
    const { HeadingTag, children } = props;
    return (
        <Heading
            HeadingTag={HeadingTag}
            fontSize='headline'
            children={children}
        />
    );
}

export function LabelHeading(props: PropsState) {
    const { HeadingTag, children } = props;
    return (
        <Heading HeadingTag={HeadingTag} fontSize='label' children={children} />
    );
}

const { typography, colors } = theme;

const createHeadingStyle = (fontSize: FontSizeType) => css`
    color: ${colors.grey[100]};
    font-weight: ${typography.fontWeight.bold};
    font-size: ${typography.fontSize[fontSize]};
`;
