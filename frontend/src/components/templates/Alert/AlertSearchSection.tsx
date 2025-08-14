import SearchBar from '../../organisms/Common/SearchBar';

export default function AlertSearchSection() {
    return (
        <SearchBar
            searchBarTitle='어디 안전정보를 확인해볼까요?'
            searchBarMessage='의 안전정보'
            pageName='safety'
            mountainData={mountainData}
        />
    );
}

const mountainData = ['설악산', '한라산', '지리산'];
