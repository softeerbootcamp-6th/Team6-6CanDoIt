import CommonText from '../../atoms/Text/CommonText.tsx';
import DaySelector from '../DaySelector/DaySelector.tsx';
import TimeRow from '../TimeRow/TimeRow.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

export default function AlertModalContent() {
    return (
        <div css={alertContentStyle}>
            <CommonText {...sectionTitleTextProps}>날짜</CommonText>
            <DaySelector />
            <CommonText {...sectionTitleTextProps}>시간</CommonText>
            <TimeRow label='시작' day='오늘' />
            <TimeRow label='종료' day='내일' />
        </div>
    );
}

const sectionTitleTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'greyOpacityWhite-40',
} as const;

const { colors } = theme;

const alertContentStyle = css`
    width: 100%;
    display: flex;
    box-sizing: border-box;
    background-color: ${colors.greyOpacityWhite[98]};
    flex-direction: column;
    gap: 1rem;
    padding: 1.75rem 1.5rem;
`;
