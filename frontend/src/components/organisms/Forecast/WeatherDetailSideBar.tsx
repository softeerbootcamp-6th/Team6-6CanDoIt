import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import DetailSideBarContentColumn from './DetailSideBarContentColumn';
import DetailSideBarHeader from '../../molecules/Forecast/DetailSideBarHeader';
import DetailSideBarSummary from '../../molecules/Forecast/DetailSideBarSummary';
import {
    convertToIconName,
    covertToWeatherByIconName,
} from '../../../utils/utils';

interface PropsState {
    type: string;
    courseAltitude?: number;
    onClose: () => void;
    card: {
        data: CardData;
        backgroundType?: Background;
        title?: string;
        courseAltitude?: number;
    } | null;
}

interface CardData {
    dateTime: string;
    hikingActivity: HikingActivityStatus;
    temperature: number;
    apparentTemperature: number;
    temperatureDescription: string;
    precipitation: string;
    probabilityDescription: string;
    precipitationType: string;
    sky: string;
    skyDescription: string;
    windSpeed: number;
    windSpeedDescription: string;
    humidity: number;
    humidityDescription: string;
    highestTemperature: number;
    lowestTemperature: number;
    title?: string;
}

type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';
type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';

const { colors } = theme;

export default function WeatherDetailSideBar({
    type,
    courseAltitude,
    onClose,
    card,
}: PropsState) {
    if (!card) return null;

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
        precipitationType,
    } = card.data;

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
        precipitationType,
    };

    const convertedIconName = convertToIconName({
        precipitationType,
        sky,
    });

    const backgroundType: Background =
        covertToWeatherByIconName(convertedIconName);

    return (
        <div css={wrapperStyles(backgroundType)}>
            <div css={topSectionStyles}>
                <DetailSideBarHeader
                    courseAltitude={courseAltitude}
                    onClose={onClose}
                    type={type}
                />
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

const wrapperStyles = (background: Background) => css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 60rem;
    padding: 3rem;
    box-sizing: border-box;
    background-color: ${colors.accentWeather[background]};
    animation: slideIn 0.3s ease-out forwards;

    @keyframes slideIn {
        from {
            transform: translateX(100%);
        }
        to {
            transform: translateX(0);
        }
    }
`;

const topSectionStyles = css`
    height: 15%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
`;
