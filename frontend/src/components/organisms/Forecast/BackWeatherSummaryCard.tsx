import BackWeatherCardHeader from '../../molecules/Forecast/BackWeatherCardHeader';
import BackWeatherCardTimeTemperature from './BackWeatherCardTimeTemperature';
import BackWeatherCardDetailInfoColumns from './BackWeatherCardDetailInfoColumns';

interface PropsState {
    cardData: {
        date: string;
        startTime: string;
        descentTime: string;
        mountainName: string;
        courseName: string;
        distance: number;
        sunrise: string;
        sunset: string;
        hikingActivityStatus: WeatherType;
        startForecast: ForecasetInfo;
        arrivalForecast: ForecasetInfo;
        descentForecast: ForecasetInfo;
        highestTemperature: number;
        lowestTemperature: number;
    };
}

interface ForecasetInfo {
    temperature: number;
    windSpeed: number;
    apparentTemperature: number;
    precipitationProbability: number;
    sky: string;
    humidity: number;
}

type WeatherType = '매우 좋음' | '좋음' | '약간 나쁨' | '나쁨';

export default function BackWeatherSummaryCard({ cardData }: PropsState) {
    const {
        date,
        hikingActivityStatus: hikingActivity,
        mountainName,
        courseName,
        startTime,
        descentTime,
        distance,
        startForecast,
        arrivalForecast,
        descentForecast,
        highestTemperature,
        lowestTemperature,
    } = cardData;

    const locationTemperatureData = [
        {
            title: '시작',
            temperature: startForecast.temperature,
            wind: startForecast.windSpeed,
        },
        {
            title: '최고',
            temperature: arrivalForecast.temperature,
            wind: arrivalForecast.windSpeed,
        },
        {
            title: '끝',
            temperature: descentForecast.temperature,
            wind: descentForecast.windSpeed,
        },
    ];

    const detailData = {
        apparentTemperature: {
            title: '체감온도',
            iconName: 'temperature',
            value: [
                `${startForecast.apparentTemperature}°C`,
                `${arrivalForecast.apparentTemperature}°C`,
                `${descentForecast.apparentTemperature}°C`,
            ],
        },
        precipitationProbability: {
            title: '강수량',
            iconName: 'rain',
            value: [
                `${startForecast.precipitationProbability}mm`,
                `${arrivalForecast.precipitationProbability}mm`,
                `${descentForecast.precipitationProbability}mm`,
            ],
        },
        sky: {
            title: '구름',
            iconName: 'cloudy',
            value: [
                startForecast.sky,
                arrivalForecast.sky,
                descentForecast.sky,
            ],
        },
        humidity: {
            title: '습도',
            iconName: 'humidity',
            value: [
                `${startForecast.humidity}%`,
                `${arrivalForecast.humidity}%`,
                `${descentForecast.humidity}%`,
            ],
        },
        dayTemperature: {
            title: '일최고/최저',
            iconName: 'clear-day',
            value: [`${lowestTemperature}°C`, `${highestTemperature}°C`],
        },
    };

    return (
        <>
            <BackWeatherCardHeader
                headerData={{
                    date,
                    hikingActivity,
                    mountainName,
                    courseName,
                    startTime,
                    descentTime,
                    distance,
                }}
            />
            <BackWeatherCardTimeTemperature
                locationTemperatureData={locationTemperatureData}
            />
            <BackWeatherCardDetailInfoColumns detailData={detailData} />
        </>
    );
}
