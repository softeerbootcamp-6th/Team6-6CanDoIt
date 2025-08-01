import styles from './ToggleButton.module.scss';

export default function ToggleButton() {
    return (
        <div className={styles.toggleButtonWrapper}>
            <div className={styles.circle}></div>
        </div>
    );
}
