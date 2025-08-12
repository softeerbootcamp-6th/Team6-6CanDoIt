import CommonText from '../../atoms/Text/CommonText.tsx';
import LabelTextBox from '../../atoms/LabelTextBox/LabelTextBox.tsx';
import { css } from '@emotion/react';

interface alertTime {
    day: string;
    startTime: string;
    endTime: string;
}

interface propsState {
    title: string;
    alertTimes: alertTime[];
}

export default function DayColumn({ title, alertTimes }: propsState) {
    return (
        <div css={dayColumnStyle}>
            <CommonText {...timeLabelText}>{title}</CommonText>
            <div css={inProgressListStyle}>
                {alertTimes.map((time) => (
                    <LabelTextBox>
                        {title === '요일'
                            ? time.day
                            : `${time.startTime} ~ ${time.endTime}`}
                    </LabelTextBox>
                ))}
            </div>
        </div>
    );
}

const timeLabelText = {
    TextTag: 'span',
    fontSize: 'body',
    fontWeight: 'bold',
    color: 'grey-100',
} as const;

const dayColumnStyle = css`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;

const inProgressListStyle = css`
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    height: max-content;
`;
