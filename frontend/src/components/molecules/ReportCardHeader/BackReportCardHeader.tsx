import ReporterImage from '../../atoms/ReporterImage/ReporterImage.tsx';
import MinutesAgo from '../MinutesAgo/MinutesAgo.tsx';
import { css } from '@emotion/react';

interface propsState {
    minutesAgo: number;
}

export default function BackReportCardHeader({ minutesAgo }: propsState) {
    return (
        <div css={backReportCardHeaderStyle}>
            <ReporterImage size='3.75rem' />
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
