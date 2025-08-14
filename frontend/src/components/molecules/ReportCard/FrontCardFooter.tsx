import CircleImage from '../../atoms/Image/CircleImage.tsx';
import TwoLinesEllipsis from '../../atoms/Text/TwoLinesEllipsis.tsx';
import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText.tsx';

interface propsState {
    minutesAgo: number;
    comment: string;
}

export default function FrontCardFooter({ minutesAgo, comment }: propsState) {
    return (
        <div css={frontCardFooterStyle}>
            <CircleImage />
            <TwoLinesEllipsis>{comment}</TwoLinesEllipsis>
            <CommonText {...minutesAgoProps}>{`${minutesAgo}분전`}</CommonText>
        </div>
    );
}

const minutesAgoProps = {
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
