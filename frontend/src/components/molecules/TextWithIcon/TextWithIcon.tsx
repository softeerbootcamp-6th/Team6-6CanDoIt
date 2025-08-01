import Icon from '../../atoms/Icon/Icons';
import styles from './TextWithIcon.module.scss';

interface PropsState {
    text: string;
    iconName: string;
}

export default function TextWithIcon(props: PropsState) {
    const { text, iconName } = props;

    return (
        <div className={styles.wrapper}>
            <Icon
                name={iconName}
                width={1.5}
                height={1.5}
                color='black'
                opacity={0.4}
                WrapperHeight='1.5rem'
                WrapperWidth='1.5rem'
            />
            <span className={styles.text}>{text}</span>
        </div>
    );
}
