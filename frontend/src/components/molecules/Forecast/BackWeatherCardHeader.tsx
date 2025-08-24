import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { WeatherIndexVivid } from '../../atoms/Text/WeatherIndex';
import { formatDateToKorean } from '../../../utils/utils';

interface PropsState {
    headerData: {
        date: string;
        hikingActivity: WeatherType;
        mountainName: string;
        courseName: string;
        startTime: string;
        descentTime: string;
        distance: number;
    };
}

type WeatherType = '매우좋음' | '좋음' | '보통' | '나쁨';

export default function BackWeatherCardHeader({ headerData }: PropsState) {
    const {
        date,
        hikingActivity,
        mountainName,
        courseName,
        startTime,
        descentTime,
        distance,
    } = headerData;

    return (
        <div css={wrapperStyles}>
            <div css={lineStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='label'
                    fontWeight='bold'
                    color='grey-30'
                >
                    {formatDateToKorean(date)}
                </CommonText>
                <WeatherIndexVivid type={hikingActivity} />
            </div>
            <div css={lineStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='medium'
                    color='grey-70'
                >
                    {`${mountainName} ${courseName} (${distance}km)`}
                </CommonText>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='medium'
                    color='grey-70'
                >
                    {formatTimeRange(startTime, descentTime)}
                </CommonText>
            </div>
        </div>
    );
}

function formatTimeRange(startTime: string, endTime: string): string {
    const formatHour = (time: string) => {
        const [hourStr] = time.split(':');
        let hour = Number(hourStr);
        const period = hour >= 12 ? 'PM' : 'AM';
        if (hour > 12) hour -= 12;
        if (hour === 0) hour = 12;
        return `${hour}${period}`;
    };

    return `${formatHour(startTime)} ~ ${formatHour(endTime)}`;
}

const lineStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;
