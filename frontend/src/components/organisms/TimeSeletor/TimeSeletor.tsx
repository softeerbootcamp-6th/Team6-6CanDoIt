import styles from './TimeSeletor.module.scss';
import WeatherCell from '../../molecules/WeatherCell/WeatherCell';
import ToggleButton from '../../atoms/ToggleButton/ToggleButton';

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
    let size = 5;

    return (
        <div className={styles.tiemSeletor}>
            <div className={styles.header}>
                <div className={styles.headerLeft}>
                    <span className={styles.subTitle}>출발시간 선택</span>
                    <span className={styles.courseTime}>4시간 코스</span>
                </div>
                <div className={styles.headerRight}>
                    <span className={styles.subTitle}>
                        고도 보정하기 아이콘
                    </span>
                    <ToggleButton />
                </div>
            </div>

            <div className={styles.scrollbarWrapper}>
                <div
                    className={styles.scroll}
                    style={{ width: `${size * 5}rem` }}
                >
                    <div className={styles.scrollbarTime}>1AM</div>
                    <div className={styles.scrollbarTime}>9PM</div>
                </div>
            </div>

            <div className={styles.weatherCell}>
                {dummy.map(() => (
                    <WeatherCell />
                ))}
            </div>
        </div>
    );
}
