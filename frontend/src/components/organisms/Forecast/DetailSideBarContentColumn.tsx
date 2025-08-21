import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import DetailSideBarContent from '../../molecules/Forecast/DetailSideBarContent';

export default function DetailSideBarContentColumn({
    weatherDetailContentData,
}: any) {
    const {
        apparentTemperature,
        temperatureDescription,
        precipitation,
        probabilityDescription,
        sky,
        skyDescription,
        windSpeed,
        windSpeedDescription,
        humidity,
        humidityDescription,
    } = weatherDetailContentData;

    const weatherDetailItems = [
        {
            iconName: 'temperature',
            title: '체감온도',
            value: `${apparentTemperature}°C`,
            description: temperatureDescription,
        },
        {
            iconName: 'rain',
            title: '강수 확률',
            value: `${precipitation}%`,
            description: probabilityDescription,
        },
        {
            iconName: 'cloudy',
            title: '구름',
            value: sky,
            description: skyDescription,
        },
        {
            iconName: 'wind',
            title: '풍속/ 풍향',
            value: `${windSpeed}m/s`,
            description: windSpeedDescription,
        },
        {
            iconName: 'humidity',
            title: '습도',
            value: `${humidity}%`,
            description: humidityDescription,
        },
    ];

    return (
        <div css={wrapperStyles}>
            {weatherDetailItems.map((item) => (
                <DetailSideBarContent {...item} />
            ))}
        </div>
    );
}

const { colors } = theme;

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    height: 80%;
    padding-top: 1.25rem;
    box-sizing: border-box;
    border-top: 0.5px solid ${colors.grey[40]};
    gap: 8%;

    & > * {
        flex: 1;
    }
`;
