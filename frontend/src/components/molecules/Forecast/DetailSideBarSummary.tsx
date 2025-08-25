import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { WeatherIndexVivid } from '../../atoms/Text/WeatherIndex';

interface PropsState {
    temperature: number;
    hikingActivity: HikingActivity;
}

type HikingActivity = '매우 좋음' | '좋음' | '약간 나쁨' | '나쁨';

export default function DetailSideBarSummary({
    temperature,
    hikingActivity,
}: PropsState) {
    return (
        <div
            css={css`
                display: flex;
                justify-content: space-between;
                align-items: center;
            `}
        >
            <CommonText
                TextTag='span'
                fontSize='display'
                fontWeight='regular'
                color='grey-0'
            >
                {`${temperature}°C`}
            </CommonText>
            <WeatherIndexVivid type={hikingActivity} />
        </div>
    );
}
