import DayColumn from '../DayColumn/DayColumn.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface alertTime {
    day: string;
    startTime: string;
    endTime: string;
}

interface propsState {
    alertTimes: alertTime[];
}

export default function InProgressModalContent({ alertTimes }: propsState) {
    return (
        <div css={contentStyle}>
            {['요일', '시간'].map((title) => (
                <DayColumn title={title} alertTimes={alertTimes} />
            ))}
        </div>
    );
}

const { colors } = theme;

const contentStyle = css`
    width: 100%;
    display: flex;
    box-sizing: border-box;
    background-color: ${colors.greyOpacityWhite[98]};
    gap: 1.5rem;
    padding: 1.5rem 1.5rem 2rem;
`;
