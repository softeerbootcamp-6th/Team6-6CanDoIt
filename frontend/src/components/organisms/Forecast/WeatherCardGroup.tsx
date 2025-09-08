import { css } from '@emotion/react';
import { WeatherIndexLight } from '../../atoms/Text/WeatherIndex';
import WeatherCard from './WeatherCard';
import svg from '../../../assets/line.svg';

interface PropsState {
    weatherDataObjs: {
        startCard: WeatherData;
        arrivalCard: WeatherData;
        descentCard: WeatherData;
        adjustedArrivalCard: WeatherData;
    };
    isToggleOn: boolean;
    courseAltitude: number;
    onSidebar: (
        backgroundType: Background,
        title: string,
        courseAltitude: number,
        data: WeatherData,
    ) => void;
}

interface WeatherData {
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

type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';
type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';

export default function WeatherCardGroup({
    courseAltitude,
    onSidebar,
    isToggleOn,
    weatherDataObjs,
}: PropsState) {
    const { startCard, arrivalCard, descentCard, adjustedArrivalCard } =
        weatherDataObjs;

    const selectedArrivalCard = isToggleOn ? adjustedArrivalCard : arrivalCard;
    const weatherDataList = [
        { ...startCard, title: '시작지점' },
        { ...selectedArrivalCard, title: '최고점' },
        { ...descentCard, title: '끝지점' },
    ];
    return (
        <div css={weatherSummaryWrapperStyles}>
            <div css={weatherCardWrapperStyles}>
                {weatherDataList.map((data, index) => {
                    const {
                        sky,
                        windSpeed,
                        skyDescription,
                        temperature,
                        precipitationType,
                    } = data;
                    return (
                        <WeatherCard
                            key={index}
                            title={data.title!}
                            weatherIconName={sky}
                            courseAltitude={
                                index === 1 ? courseAltitude : undefined
                            }
                            weatherIconText={skyDescription}
                            windSpeed={windSpeed}
                            temperature={temperature}
                            precipitationType={precipitationType}
                            onClick={(backgroundType, title, courseAltitude) =>
                                onSidebar(
                                    backgroundType,
                                    title,
                                    courseAltitude!,
                                    data,
                                )
                            }
                        />
                    );
                })}
            </div>
            <img css={lineImageStyles} src={svg} />
            <WeatherIndexLight type={weatherDataList[0].hikingActivity} />
        </div>
    );
}

const weatherCardWrapperStyles = css`
    display: flex;
    width: 100%;
    justify-content: space-evenly;

    & > :nth-of-type(1) {
        transform: translateY(4rem);
        cursor: pointer;

        &:hover {
            transform: translateY(4rem) scale(1.15);
        }
    }

    & > :nth-of-type(2) {
        transform: translateY(-3rem);
        cursor: pointer;

        &:hover {
            transform: translateY(-3rem) scale(1.15);
        }
    }

    & > :nth-of-type(3) {
        transform: translateY(4rem);
        cursor: pointer;

        &:hover {
            transform: translateY(4rem) scale(1.15);
        }
    }
`;

const weatherSummaryWrapperStyles = css`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
`;

const lineImageStyles = css`
    position: absolute;
    top: 36%;
    z-index: -1;
    width: 60%;
    height: 32%;
`;
