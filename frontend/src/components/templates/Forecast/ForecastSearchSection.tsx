import SearchBar from '../../organisms/Common/SearchBar';

export default function ForecastSearchSection() {
    return <SearchBar {...searchBarProps} />;
}

const mountainData = ['설악산', '한라산', '지리산'];
const courseData = ['코스1', '코스2', '코스3'];

const searchBarProps = {
    searchBarTitle: '어디 날씨를 확인해볼까요?',
    searchBarMessage: '를 오르는',
    pageName: 'main',
    mountainData,
    courseData,
} as const;
