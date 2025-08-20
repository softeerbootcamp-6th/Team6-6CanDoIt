import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import DetailSideBarContentColumn from './DetailSideBarContentColumn';

const { colors } = theme;

export default function WeatherDetailSideBar({ selectedWeatherData }: any) {
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
    } = selectedWeatherData;

    const weatherDetailContentData = {
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
    };

    return (
        <div css={wrapperStyles}>
            <div css={temp1Styles}>top Styles</div>
            <DetailSideBarContentColumn
                weatherDetailContentData={weatherDetailContentData}
            />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 60rem;
    padding: 3rem;
    box-sizing: border-box;
    background-color: ${colors.primary.normal};
`;

const temp1Styles = css`
    height: 15%;
`;
