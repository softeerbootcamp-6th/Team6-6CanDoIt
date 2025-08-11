import CommonText from '../../atoms/Text/CommonText.tsx';
import LabelTextBox from '../../atoms/LabelTextBox/LabelTextBox.tsx';
import { css } from '@emotion/react';

type alertTime = {
    day: string;
    startTime: string;
    endTime: string;
};

export default function DayColumn({
    title,
    alertTimes,
}: {
    title: string;
    alertTimes: alertTime[];
}) {
    return (
        <div css={dayColumnStyle}>
            <CommonText {...timeLabelText}>{title}</CommonText>
            <div css={inProgressListStyle}>
                {alertTimes.map((t) => (
                    <LabelTextBox>
                        {title === '요일'
                            ? t.day
                            : `${t.startTime} ~ ${t.endTime}`}
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
