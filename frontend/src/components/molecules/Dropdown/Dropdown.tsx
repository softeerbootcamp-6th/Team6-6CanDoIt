import styles from './Dropdown.module.scss';
import UlList from '../../atoms/UlList/UlList.tsx';

// 드롭다운의 제목, 옵션 리스트를 받고 렌더링
interface propsState {
    title: string;
    options: string[];
}

export default function Dropdown(props: propsState) {
    const { title, options } = props;
    let isOpen = true;

    return (
        <div className={styles.wholeWrapper}>
            <div className={styles.selectButton}>
                <h3 className={`text-grey-100 fontSize-label fontWeight-bold`}>
                    {title}
                </h3>
            </div>
            {isOpen && (
                <div className={styles.dropdownWrapper}>
                    <div className={styles.dropdownTitleWrapper}>
                        <h3 className={`fontSize-label fontWeight-bold`}>
                            {title}
                        </h3>
                    </div>
                    <UlList list={options} />
                </div>
            )}
        </div>
    );
}
