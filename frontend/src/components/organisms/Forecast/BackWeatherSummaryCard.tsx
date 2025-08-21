import BackWeatherCardHeader from '../../molecules/Forecast/BackWeatherCardHeader';
import BackWeatherCardTimeTemperature from './BackWeatherCardTimeTemperature';
import BackWeatherCardDetailInfoColumns from './BackWeatherCardDetailInfoColumns';

export default function BackWeatherSummaryCard() {
    return (
        <>
            <BackWeatherCardHeader />
            <BackWeatherCardTimeTemperature />
            <BackWeatherCardDetailInfoColumns />
        </>
    );
}
