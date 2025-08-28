import { css } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons.tsx';
import SelectorText from '../../atoms/Text/SelectorText.tsx';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    time: string;
    temperature: number;
    iconName: string;
}

export default function WeatherCell({
    time,
    temperature,
    iconName,
}: PropsState) {
    const newFormatTime = formatTime(time);

    return (
        <div css={wrapperStyles(newFormatTime)}>
            <SelectorText>{time === '' ? '' : newFormatTime}</SelectorText>
            <Icon
                name={iconName}
                width={1.5}
                height={1.5}
                color='grey-80'
                opacity={0.8}
            />
            <SelectorText>
                {temperature === 0 ? '' : `${temperature}°C`}
            </SelectorText>
        </div>
    );
}

function formatTime(timeStr: string) {
    const date = new Date(timeStr);
    const now = new Date();

    const dateOnly = new Date(
        date.getFullYear(),
        date.getMonth(),
        date.getDate(),
    );
    const nowDateOnly = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate(),
    );

    const diffDays = Math.floor(
        (dateOnly.getTime() - nowDateOnly.getTime()) / (1000 * 60 * 60 * 24),
    );

    const isMidnight = date.getHours() === 0 && date.getMinutes() === 0;

    if (isMidnight) {
        if (diffDays === 0) return '오늘';
        else if (diffDays === 1) return '내일';
        else if (diffDays === 2) return '모레';
    }

    let hours = date.getHours();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    if (hours === 0) hours = 12;
    else if (hours > 12) hours -= 12;

    return `${hours} ${ampm}`;
}

const { colors } = theme;

const wrapperStyles = (timeLabel: string) => css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    flex: 0 0 auto;
    scroll-snap-align: start;
    width: 5rem;
    height: 7rem;

    span:first-of-type {
        background-color: ${timeLabel === '오늘' ||
        timeLabel === '내일' ||
        timeLabel === '모레'
            ? colors.greyOpacityWhite[80]
            : 'transparent'};
        border-radius: 1.5rem;
        width: 4rem;
    }
`;
