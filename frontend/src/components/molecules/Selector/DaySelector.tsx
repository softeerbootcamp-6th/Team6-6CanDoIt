import DayButton from '../../atoms/Button/DayButton.tsx';
import { css } from '@emotion/react';

export default function DaySelector() {
    return (
        <div css={daySelectorStyle}>
            {dayList.map((day) => (
                <DayButton>{day}</DayButton>
            ))}
        </div>
    );
}

const dayList = ['오늘', '내일', '모레', '글피'];

const daySelectorStyle = css`
    width: 100%;
    display: flex;
    padding-left: 1rem;
    box-sizing: border-box;
    gap: 1rem;
`;
