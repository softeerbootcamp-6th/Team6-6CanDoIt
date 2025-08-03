// URL을 보고 홈, 제보에 맞게 검색바를 다르게 렌더링
import Dropdown from '../../molecules/Dropdown/Dropdown.tsx';
import styles from './SearchBar.module.scss';

const mountainCourseData = [
    {
        title: '산',
        options: ['설악산', '한라산', '지리산'],
    },
    {
        title: '코스',
        options: ['코스1', '코스2', '코스3'],
    },
];

const weekdayData = {
    title: '요일은?',
    options: ['월', '화', '수', '목', '금', '토', '일'],
};

export default function SearchBar() {
    const searchBarTitle = '어디 날씨를 확인해볼까요?';
    const searchBarMessage = '를 오르는';
    let isHomaPage = true;

    return (
        <div className={styles.search}>
            <h2 className={`text-grey-100 fontSize-label fontWeight-bold`}>
                {searchBarTitle}
            </h2>
            <div className={styles.searchBarWrapper}>
                {mountainCourseData.map((data) => {
                    return (
                        <Dropdown
                            key={data.title}
                            title={data.title}
                            options={data.options}
                        />
                    );
                })}
                {searchBarMessage}
                {isHomaPage && (
                    <Dropdown
                        title={weekdayData.title}
                        options={weekdayData.options}
                    />
                )}
            </div>
        </div>
    );
}
