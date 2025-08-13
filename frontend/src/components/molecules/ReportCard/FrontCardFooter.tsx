import CircleImage from '../../atoms/Image/CircleImage.tsx';
import TwoLinesEllipsis from '../../atoms/Text/TwoLinesEllipsis.tsx';
import MinutesAgo from '../MinutesAgo_delete/MinutesAgo.tsx';
import { css } from '@emotion/react';

interface propsState {
    minutesAgo: number;
    comment: string;
}

export default function FrontCardFooter({ minutesAgo, comment }: propsState) {
    return (
        <div css={frontCardFooterStyle}>
            <CircleImage />
            <TwoLinesEllipsis>{comment}</TwoLinesEllipsis>
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
