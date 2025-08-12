import ReporterImage from '../../atoms/ReporterImage/ReporterImage.tsx';
import CardCommentEllipsis from '../CardCommentEllipsis/CardCommentEllipsis.tsx';
import MinutesAgo from '../MinutesAgo/MinutesAgo.tsx';
import { css } from '@emotion/react';

interface propsState {
    minutesAgo: number;
    comment: string;
}

export default function FrontCardFooter({ minutesAgo, comment }: propsState) {
    return (
        <div css={frontCardFooterStyle}>
            <ReporterImage />
            <CardCommentEllipsis>{comment}</CardCommentEllipsis>
            <MinutesAgo value={minutesAgo} />
        </div>
    );
}

const frontCardFooterStyle = css`
    width: 23rem;
    height: 2.875rem;
    display: flex;
    justify-content: space-between;
`;
