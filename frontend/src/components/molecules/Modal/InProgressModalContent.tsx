import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import CommonText from '../../atoms/Text/CommonText.tsx';
import FilterLabelTextBox from '../../atoms/Text/FilterLabelTextBox.tsx';

interface alertTime {
    day: string;
    startTime: string;
    endTime: string;
}

interface propsState {
    alertTime: alertTime;
}

export default function InProgressModalContent({ alertTime }: propsState) {
    return (
        <div css={contentStyle}>
            <div css={columnStyle}>
                <CommonText {...labelTextStyle}>요일</CommonText>
                <FilterLabelTextBox>{alertTime.day}</FilterLabelTextBox>
            </div>
            <div css={columnStyle}>
                <CommonText {...labelTextStyle}>시간</CommonText>
                <FilterLabelTextBox>
                    {`${alertTime.startTime} ~ ${alertTime.endTime}`}
                </FilterLabelTextBox>
            </div>
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

const columnStyle = css`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;

const labelTextStyle = {
    TextTag: 'span',
    fontSize: 'body',
    fontWeight: 'bold',
    color: 'grey-100',
} as const;
