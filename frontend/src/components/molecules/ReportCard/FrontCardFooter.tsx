import CircleImage from '../../atoms/Image/CircleImage.tsx';
import TwoLinesEllipsis from '../../atoms/Text/TwoLinesEllipsis.tsx';
import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText.tsx';

interface PropsState {
    timeAgo: string;
    comment: string;
}

export default function FrontCardFooter({ timeAgo, comment }: PropsState) {
    return (
        <div css={frontCardFooterStyle}>
            <CircleImage />
            <TwoLinesEllipsis>{comment}</TwoLinesEllipsis>
            <CommonText {...timeAgoProps}>{timeAgo}</CommonText>
        </div>
    );
}

const timeAgoProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'greyOpacityWhite-60',
    lineHeight: 1.3,
} as const;

const frontCardFooterStyle = css`
    width: 23rem;
    height: 2.875rem;
    display: flex;
    justify-content: space-between;
`;
