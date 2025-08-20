import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { WeatherIndexVivid } from '../../atoms/Text/WeatherIndex';

interface PropsState {
    temperature: string;
    hikingActivity: HikingActivity;
}

type HikingActivity = '매우좋음' | '좋음' | '보통' | '나쁨';

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
