import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import DetailSideBarContentColumn from './DetailSideBarContentColumn';
import DetailSideBarHeader from '../../molecules/Forecast/DetailSideBarHeader';
import DetailSideBarSummary from '../../molecules/Forecast/DetailSideBarSummary';

const { colors } = theme;

export default function WeatherDetailSideBar({
    selectedWeatherData,
    type,
    onClose,
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
        hikingActivity,
        temperature,
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

    const weatherHeaderData = {
        hikingActivity,
        temperature,
        type,
    };

    return (
        <div css={wrapperStyles}>
            <div css={topSectionStyles}>
                <DetailSideBarHeader onClose={onClose} type={type} />
                <DetailSideBarSummary
                    temperature={temperature}
                    hikingActivity={hikingActivity}
                />
            </div>
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

const topSectionStyles = css`
    height: 15%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
`;
