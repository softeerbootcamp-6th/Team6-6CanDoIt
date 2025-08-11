import DayButton from '../../atoms/DayButton/DayButton.tsx';
import { css } from '@emotion/react';

export default function DaySelector() {
    return (
        <div css={daySelectorStyle}>
            <DayButton>오늘</DayButton>
            <DayButton>내일</DayButton>
            <DayButton>모레</DayButton>
            <DayButton>글피</DayButton>
        </div>
    );
}

const daySelectorStyle = css`
    width: 100%;
    display: flex;
    padding-left: 1rem;
    box-sizing: border-box;
    gap: 1rem;
`;
