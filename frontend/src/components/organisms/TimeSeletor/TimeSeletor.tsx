import styles from './TimeSeletor.module.scss';

import WeatherCell from '../../molecules/WeatherCell/WeatherCell';

const dummy = [
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
];

export default function TimeSeletor() {
    return (
        <div className={styles.tiemSeletor}>
            <div className={styles.header}></div>
            <div className={styles.scrollbarWrapper}></div>

            <div className={styles.weatherCell}>
                {dummy.map(() => (
                    <WeatherCell />
                ))}
            </div>
        </div>
    );
}
