import styles from './ForecastPage.module.scss';
import WeatherCard from '../../components/organisms/WeatherCard/WeatherCard';
import TimeSeletor from '../../components/organisms/TimeSeletor/TimeSeletor';
import { DisplayHeading } from '../../components/atoms/Heading/Heading';
import CommonText from '../../components/atoms/Text/CommonText';

export default function ForecastPage() {
    return (
        <div className={styles.wrapper}>
            <div className={styles.contentSection}>
                <DisplayHeading HeadingTag='h1'>
                    <div className={styles.headingWrapper}>
                        오전 9시에
                        <CommonText
                            TextTag='span'
                            fontSize='display'
                            fontWeight='regular'
                            color='greyOpacityWhite-40'
                        >
                            출발하면
                        </CommonText>
                        바람막이가 필요할 거에요
                    </div>
                </DisplayHeading>
                <div className={styles.weatherCardWrapper}>
                    <WeatherCard />
                    <WeatherCard />
                    <WeatherCard />
                </div>
                <TimeSeletor />
            </div>
        </div>
    );
}
