// 산 이름, 날씨 정보를 받고 렌더링
import WeatherContent from '../../molecules/WeatherContent/WeatherContent.tsx';
import styles from './MountainCard.module.scss';
import Icon from '../../atoms/Icon/Icons.tsx';

interface propsState {
    mountainName: string;
    weatherIconName: string;
    surfaceTemperature: number;
    summitTemperature: number;
}

export default function MountainCard(props: propsState) {
    const {
        mountainName,
        weatherIconName,
        surfaceTemperature,
        summitTemperature,
    } = props;

    return (
        <div className={styles.cardWrapper}>
            <div className={styles.cardHeader}>
                <div className={styles.weatherInfo}>
                    <Icon
                        name={weatherIconName}
                        WrapperWidth='1.5rem'
                        WrapperHeight='1.25rem'
                        width={1.5}
                        height={1.5}
                    />
                    <WeatherContent
                        description='지표면 '
                        temperature={surfaceTemperature}
                    />
                    <span
                        className={`fontSize-caption fontWeight-regular text-grey-opacity-50`}
                    >
                        {' | '}
                    </span>
                    <WeatherContent
                        description='정상 '
                        temperature={summitTemperature}
                    />
                </div>
                <Icon
                    name='narrow-right'
                    WrapperWidth='2.5rem'
                    WrapperHeight='2.5rem'
                    width={1.9}
                    height={1.9}
                    color='black'
                    shape='circle'
                    backgroundColor='bg-grey-100'
                />
            </div>
            <div className={styles.cardFooter}>
                <h2
                    className={`text-grey-100 fontWeight-bold fontSize-headline`}
                >
                    {mountainName}
                </h2>
                <p className={styles.weatherNavMessage}>날씨 보러가기</p>
            </div>
        </div>
    );
}
