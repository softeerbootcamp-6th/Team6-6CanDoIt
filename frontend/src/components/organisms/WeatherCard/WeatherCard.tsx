import styles from './WeatherCard.module.scss';
import TextWithIcon from '../../molecules/TextWithIcon/TextWithIcon';
import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';

export default function WeatherCard() {
    return (
        <div className={`${styles.card}`}>
            <div className={styles.header}>
                <span className={styles.title}>시작 지점</span>

                <Icon
                    name='narrow-right'
                    color='grey-0'
                    width={2}
                    height={2}
                    opacity={0.8}
                />
            </div>
            <div className={styles.footer}>
                <span className={styles.iconWrapper}>
                    <TextWithIcon
                        iconName='rain'
                        text='비옴'
                        color='greyOpacity-60'
                    />
                    <TextWithIcon
                        iconName='rain'
                        text='2m/s'
                        color='greyOpacity-60'
                    />
                </span>
                <CommonText
                    TextTag='span'
                    fontSize='display'
                    fontWeight='medium'
                    color='grey-0'
                >
                    20°C
                </CommonText>
            </div>
        </div>
    );
}
