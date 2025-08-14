import { css } from '@emotion/react';
import { DisplayHeading } from '../../atoms/Heading/Heading';
import CommonText from '../../atoms/Text/CommonText';

export default function DetailTitle() {
    return (
        <DisplayHeading HeadingTag='h1'>
            <span css={displayHeadingStyle}>
                오전 9시
                <CommonText
                    TextTag='span'
                    fontSize='display'
                    fontWeight='regular'
                    color='greyOpacityWhite-40'
                >
                    에 출발하면
                </CommonText>
            </span>
            <span css={lineGroupStyle}>바람막이가 필요할 거에요</span>
        </DisplayHeading>
    );
}

const displayHeadingStyle = css`
    line-height: 1.5;
    white-space: normal;
    margin-bottom: 1rem;
`;

const lineGroupStyle = css`
    display: block;
`;
