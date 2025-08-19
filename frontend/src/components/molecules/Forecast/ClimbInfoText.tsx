import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';

interface PropsState {
    totalDuration: number;
    totalDistance: number;
}

export default function ClimbInfoText({
    totalDuration,
    totalDistance,
}: PropsState) {
    const { hour, min } = parseDuration(totalDuration);

    return (
        <p css={textWrapperStyles}>
            <CommonText
                {...WhiteTextProps}
            >{`${hour}시간 ${min}분`}</CommonText>
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

function parseDuration(totalDuration: number): { hour: number; min: number } {
    const hour = Math.floor(totalDuration);
    const min = Math.round((totalDuration - hour) * 60);
    return { hour, min };
}
