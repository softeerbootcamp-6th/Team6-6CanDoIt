import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';
import styles from './WeatherCell.module.scss';

export default function WeatherCell() {
    return (
        <div className={styles.cellWrapper}>
            <span>{'123'}</span>
            <Icon
                name='clear-day'
                width={1.5}
                height={1.5}
                color='grey-60'
                opacity={0.8}
            />
            <CommonText TextTag='span' fontSize='body' fontWeight='bold'>
                20°C
            </CommonText>
        </div>
    );
}
