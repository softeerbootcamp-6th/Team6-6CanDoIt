// URL을 보고 홈, 제보에 맞게 검색바를 다르게 렌더링
import Dropdown from '../../molecules/Dropdown/Dropdown.tsx';
import { css } from '@emotion/react';
import { LabelHeading } from '../../atoms/Heading/Heading.tsx';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';
import SearchBarText from '../../atoms/Text/SearchBarText.tsx';

interface propsState {
    searchBarTitle: string;
    searchBarMessage: string;
    isHomePage: boolean;
    mountainCourseData: { title: string; options: string[] }[];
}

export default function SearchBar(props: propsState) {
    const { searchBarTitle, searchBarMessage, isHomePage, mountainCourseData } =
        props;

    return (
        <div css={searchBarContainerStyle}>
            <LabelHeading HeadingTag='h2'>{searchBarTitle}</LabelHeading>
            <div css={searchBarStyle}>
                {mountainCourseData.map((data) => {
                    return (
                        <Dropdown title={data.title} options={data.options} />
                    );
                })}
                <SearchBarText>{searchBarMessage}</SearchBarText>
                {isHomePage && (
                    <Dropdown
                        title={weekdayData.title}
                        options={weekdayData.options}
                    />
                )}
                <button css={searchButtonStyle}>
                    <Icon {...searchButtonIconProps} />
                </button>
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
    color: ${getColor({ colors, colorString: 'grey-100' })};
    border-radius: 6.25rem;
    background-color: ${getColor({
        colors,
        colorString: 'greyOpacityWhite-70',
    })};
    backdrop-filter: blur(50px);
    width: max-content;
    padding: 0.75rem;
`;

const searchButtonStyle = css`
    width: 3rem;
    height: 3rem;

    background-color: ${getColor({
        colors,
        colorString: 'greyOpacityWhite-70',
    })};
    backdrop-filter: blur(50px);

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
