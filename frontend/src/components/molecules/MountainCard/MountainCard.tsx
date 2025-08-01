// 산 이름, 날씨 정보를 받고 렌더링
import WeatherContent from '../../atoms/WeatherContent/WeatherContent.tsx';
import styles from './MountainCard.module.scss';

interface propsState {
    mountainName: string;
    surfaceTemperature: number;
    summitTemperature: number;
}

export default function MountainCard(props: propsState) {
    const { mountainName, surfaceTemperature, summitTemperature } = props;

    return (
        <div className={styles.cardWrapper}>
            <div>
                <WeatherContent
                    surfaceTemperature={surfaceTemperature}
                    summitTemperature={summitTemperature}
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
