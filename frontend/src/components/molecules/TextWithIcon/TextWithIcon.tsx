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
                color='grey-0'
                opacity={0.4}
            />
            <span className={styles.text}>{text}</span>
        </div>
    );
}
