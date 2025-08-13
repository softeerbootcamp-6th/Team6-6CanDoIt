import CircleImage from '../../atoms/Image/CircleImage.tsx';
import MinutesAgo from '../MinutesAgo_delete/MinutesAgo.tsx';
import { css } from '@emotion/react';

interface propsState {
    minutesAgo: number;
}

export default function BackReportCardHeader({ minutesAgo }: propsState) {
    return (
        <div css={backReportCardHeaderStyle}>
            <CircleImage size='3.75rem' />
            <MinutesAgo value={minutesAgo} />
        </div>
    );
}

const backReportCardHeaderStyle = css`
    width: 23rem;
    height: 3.75rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
`;
