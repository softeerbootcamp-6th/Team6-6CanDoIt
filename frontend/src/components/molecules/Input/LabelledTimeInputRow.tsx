import CommonText from '../../atoms/Text/CommonText.tsx';
import TimeInput from './TimeInput.tsx';
import { css } from '@emotion/react';

interface propsState {
    label: string;
    day: string;
}

export default function LabelledTimeInputRow({ label, day }: propsState) {
    return (
        <div css={timeRowStyle}>
            <CommonText {...timeLabelText}>{label}</CommonText>
            <TimeInput day={day} />
        </div>
    );
}

const timeLabelText = {
    TextTag: 'span',
    fontSize: 'body',
    fontWeight: 'bold',
    color: 'grey-100',
} as const;

const timeRowStyle = css`
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0 1rem;
`;
