import type { ReactNode } from 'react';
import { css } from '@emotion/react';
import type { FontSizeType } from '../../../types/themeTypes';
import { createTextStyle } from '../../../utils/utils.ts';

type HeadingTagType = 'h1' | 'h2' | 'h3' | 'h4' | 'h5' | 'h6';

interface PropsState {
    HeadingTag: HeadingTagType;
    fontSize?: FontSizeType;
    children: string | ReactNode;
}

function Heading(props: PropsState) {
    const { HeadingTag, fontSize, children } = props;

    const headingStyle = css`
        ${createTextStyle({ fontSize, fontWeight: 'bold', color: 'grey-100' })}
    `;

    return <HeadingTag css={headingStyle}>{children}</HeadingTag>;
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
