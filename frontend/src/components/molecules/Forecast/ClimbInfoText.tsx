import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { parseDuration } from '../../../utils/utils';

interface PropsState {
    totalDuration: number;
    totalDistance: number;
}

export default function ClimbInfoText({
    totalDuration,
    totalDistance,
}: PropsState) {
    function formatDurationText(totalDuration: number): string {
        const { hour, min } = parseDuration(totalDuration);
        const parts: string[] = [];

        if (hour > 0) parts.push(`${hour}시간`);
        if (min > 0) parts.push(`${min}분`);

        return parts.join(' ');
    }

    const timeText = formatDurationText(totalDuration);

    return (
        <p css={textWrapperStyles}>
            <CommonText {...WhiteTextProps}>{timeText}</CommonText>
            <CommonText {...TextProps}>동안</CommonText>
            <CommonText {...WhiteTextProps}>{`${totalDistance}km`}</CommonText>
            <CommonText {...TextProps}>를 올라야해요</CommonText>
        </p>
    );
}

const textWrapperStyles = css`
    & :nth-of-type(-n + 3) {
        margin-left: 0.4rem;
    }
`;

const WhiteTextProps = {
    TextTag: 'span',
    color: 'grey-100',
    fontWeight: 'bold',
    fontSize: 'body',
} as const;

const TextProps = {
    TextTag: 'span',
    color: 'grey-90',
    fontWeight: 'bold',
    fontSize: 'body',
} as const;
