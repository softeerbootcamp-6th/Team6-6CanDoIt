import styles from './WeatherContent.module.scss';

interface propsState {
    surfaceTemperature: number;
    summitTemperature: number;
}

export default function WeatherContent(props: propsState) {
    const { surfaceTemperature, summitTemperature } = props;

    return (
        <>
            <span>지표면 </span>
            <span className={styles.temperature}>{surfaceTemperature}°C</span>

            <span> | 정상 </span>
            <span className={styles.temperature}>{summitTemperature}°C</span>
        </>
    );
}
