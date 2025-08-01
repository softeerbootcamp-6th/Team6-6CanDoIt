import styles from './WeatherCard.module.scss';
import Temperature from '../../atoms/Temperature/Temperature';
import TextWithIcon from '../../molecules/TextWithIcon/TextWithIcon';
import Icon from '../../atoms/Icon/Icons';

export default function WeatherCard() {
    return (
        <div className={`${styles.card}`}>
            <div className={styles.header}>
                <span className={styles.title}>시작 지점</span>

                <Icon
                    shape='circle'
                    name='narrow-right'
                    WrapperHeight='3rem'
                    WrapperWidth='3rem'
                    backgroundColor='bg-grey-100'
                    color='black'
                    width='1.8rem'
                    height='1.8rem'
                    opacity={0.8}
                />
            </div>
            <div className={styles.footer}>
                <span className={styles.iconWrapper}>
                    <TextWithIcon iconName='rain' text='비옴' />
                    <TextWithIcon iconName='rain' text='2m/s' />
                </span>
                <Temperature temperature={20} />
            </div>
        </div>
    );
}
