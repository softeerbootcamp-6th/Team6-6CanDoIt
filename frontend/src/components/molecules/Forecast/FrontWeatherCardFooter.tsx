import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { parseDuration } from '../../../utils/utils';

interface PropsState {
    data: FooterData;
}

interface FooterData {
    mountainName: string;
    distance: number;
    courseName: string;
    duration: number;
}

export default function FrontWeatherCardFooter({ data }: PropsState) {
    const { mountainName, distance, courseName, duration } = data;

    function formatDurationText(duration: number): string {
        const { hour, min } = parseDuration(duration);
        const parts: string[] = [];

        if (hour > 0) parts.push(`${hour}시간`);
        if (min > 0) parts.push(`${min}분`);

        return parts.join(' ');
    }

    const timeText = formatDurationText(duration);

    return (
        <div>
            <CommonText
                TextTag='span'
                fontSize='headline'
                fontWeight='bold'
                color='grey-100'
            >
                {mountainName}
            </CommonText>
            <div css={wrapperStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='body'
                    fontWeight='medium'
                    color='greyOpacityWhite-50'
                >
                    {`${courseName} ${distance}km`}
                </CommonText>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='medium'
                    color='greyOpacityWhite-50'
                >
                    {timeText}
                </CommonText>
            </div>
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 0.6rem;
`;
