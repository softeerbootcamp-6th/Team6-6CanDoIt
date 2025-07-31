import Icon from '../../atoms/Icon/Icons';
import Temperature from '../../atoms/Temperature/Temperature';
import styles from './WeatherCell.module.scss';

export default function WeatherCell() {
    return (
        <div className={styles.cellWrapper}>
            <span>{'123'}</span>
            <Icon
                name='clear-day'
                WrapperHeight='1.5rem'
                WrapperWidth='1.5rem'
                width='1.5rem'
                height='1.5rem'
                color='black'
                opacity={0.8}
            />
            <Temperature temperature={12} fontSize='body' />
        </div>
    );
}
