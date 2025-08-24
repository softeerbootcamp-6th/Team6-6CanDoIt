import { css } from '@emotion/react';
import { DisplayHeading } from '../../atoms/Heading/Heading';
import CommonText from '../../atoms/Text/CommonText';

interface PropsState {
    recommendComment: string;
    scrollSeletedTime: string;
}

export default function DetailTitle({
    recommendComment,
    scrollSeletedTime,
}: PropsState) {
    function formatDateTime(input: string) {
        const date = new Date(input);
        const now = new Date();

        const today = new Date(
            now.getFullYear(),
            now.getMonth(),
            now.getDate(),
        );
        const target = new Date(
            date.getFullYear(),
            date.getMonth(),
            date.getDate(),
        );

        const diffDays = Math.round(
            (target.getTime() - today.getTime()) / (1000 * 60 * 60 * 24),
        );

        let prefix = '';
        if (diffDays === 1) prefix = '내일 ';
        else if (diffDays === 2) prefix = '모레 ';

        const hours = date.getHours();
        const period = hours < 12 ? '오전' : '오후';
        const hour12 = hours % 12 === 0 ? 12 : hours % 12;

        return `${prefix}${period} ${hour12}시`;
    }

    return (
        <DisplayHeading HeadingTag='h1'>
            <span css={displayHeadingStyle}>
                {formatDateTime(scrollSeletedTime)}
                <CommonText
                    TextTag='span'
                    fontSize='display'
                    fontWeight='regular'
                    color='greyOpacityWhite-40'
                >
                    에 출발하면
                </CommonText>
            </span>
            <span css={lineGroupStyle}>{recommendComment}</span>
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
