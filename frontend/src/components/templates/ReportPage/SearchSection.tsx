import SearchBar from '../../organisms/SearchBar/SearchBar.tsx';
import { css } from '@emotion/react';
import ChipsRow from '../../organisms/SearchSectionChipsRow/SearchSectionChipsRow.tsx';

export default function SearchSection() {
    return (
        <div css={searchWrapperStyle}>
            <ChipsRow hidden />

            <div css={searchBarStyle}>
                <SearchBar
                    searchBarTitle='어디 날씨를 확인해볼까요?'
                    searchBarMessage='의 실시간 제보'
                    pageName='report'
                    mountainData={mountainData}
                    courseData={courseData}
                />
            </div>

            <ChipsRow />
        </div>
    );
}

const searchWrapperStyle = css`
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    padding: 0 2rem;
    box-sizing: border-box;
`;

const searchBarStyle = css`
    width: 100%;
`;

const mountainData = ['설악산', '한라산', '지리산'];
const courseData = ['코스1', '코스2', '코스3'];
