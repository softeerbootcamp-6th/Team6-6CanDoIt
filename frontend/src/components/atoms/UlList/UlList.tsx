// 리스트를 받아서 ul 태그로 감싸고 반환
import styles from './UlList.module.scss';

interface propsState {
    list: string[];
}

export default function UlList(props: propsState) {
    const { list } = props;

    return (
        <ul className={styles.list}>
            {list.map((item, index) => (
                <li
                    className={`${styles['item']} fontSize-label fontWeight-bold`}
                    key={index}
                >
                    {item}
                </li>
            ))}
        </ul>
    );
}
