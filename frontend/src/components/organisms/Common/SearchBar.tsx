// URL을 보고 홈, 제보에 맞게 검색바를 다르게 렌더링
import Dropdown from '../../molecules/Dropdown/Dropdown.tsx';
import { LabelHeading } from '../../atoms/Heading/Heading.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';
import SearchBarText from '../../atoms/Text/SearchBarText.tsx';

type pageName = 'main' | 'report' | 'safety';

interface propsState {
    searchBarTitle?: string;
    searchBarMessage: string;
    pageName?: pageName;
    mountainData: string[];
    courseData?: string[];
}

export default function SearchBar(props: propsState) {
    const {
        searchBarTitle,
        searchBarMessage,
        pageName = 'main',
        mountainData,
        courseData,
    } = props;

    const isMainPage = pageName === 'main';
    const isReportPage = pageName === 'report';
    const isSafetyPage = pageName === 'safety';

    return (
        <div css={searchBarContainerStyle}>
            <LabelHeading HeadingTag='h2'>{searchBarTitle}</LabelHeading>
            <div css={searchBarStyle}>
                <Dropdown title='산' options={mountainData} />
                {(isMainPage || isReportPage) && (
                    <Dropdown title='코스' options={courseData || []} />
                )}
                <SearchBarText>{searchBarMessage}</SearchBarText>
                {isMainPage && (
                    <Dropdown
                        title={weekdayData.title}
                        options={weekdayData.options}
                    />
                )}
                {(isMainPage || isSafetyPage) && (
                    <button css={searchButtonStyle}>
                        <Icon {...searchButtonIconProps} />
                    </button>
                )}
            </div>
        </div>
    );
}

const searchButtonIconProps = {
    name: 'search-sm',
    width: 1.875,
    height: 1.875,
    color: 'grey-100',
};

const { colors } = theme;

const searchBarContainerStyle = css`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1.5rem;
`;

const searchBarStyle = css`
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: ${colors.grey[100]};
    border-radius: 6.25rem;
    background-color: ${colors.greyOpacityWhite[70]};
    width: max-content;
    padding: 0.75rem;
`;

const searchButtonStyle = css`
    width: 3rem;
    height: 3rem;
    background-color: ${colors.greyOpacityWhite[70]};
    margin-left: auto;
    border-radius: 50%;
    border: none;
    cursor: pointer;
`;

const weekdayData = {
    title: '요일은?',
    options: [
        '월요일',
        '화요일',
        '수요일',
        '목요일',
        '금요일',
        '토요일',
        '일요일',
    ],
};
