import { css } from '@emotion/react';
import MountainInfoPreview from '../molecules/Forecast/MountainInfoPreview.tsx';
import Header from '../organisms/Common/Header.tsx';
import SearchBar from '../organisms/Common/SearchBar.tsx';
import { WeatherIndexLight } from '../atoms/Text/WeatherIndex.tsx';
import CommonText from '../atoms/Text/CommonText';
import Icon from '../atoms/Icon/Icons';

export default function ForecastMain() {
    return (
        <div css={wrapperStyles}>
            <Header />
            <SearchBar {...searchBarProps} />
            <MountainInfoPreview time='0401' dist={5} />

            <WeatherIndexLight type='매우좋음' />
            <div css={downStyles}>
                <CommonText {...textProps}>
                    <span
                        css={css`
                            margin-bottom: 0.4rem;
                        `}
                    >
                        스크롤하여 초단기 날씨를
                    </span>
                    <span
                        css={css`
                            margin-bottom: 0.6rem;
                        `}
                    >
                        확인하세요
                    </span>
                </CommonText>
                <Icon
                    name='chevron-down-double'
                    color='grey-100'
                    width={3}
                    height={3}
                />
            </div>
        </div>
    );
}

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

const searchBarProps = {
    searchBarMessage: '를 오르는',
    isHomePage: true,
    mountainCourseData,
};

const textProps = {
    TextTag: 'p',
    color: 'grey-100',
    fontWeight: 'medium',
    fontSize: 'body',
    flexColoumn: true,
} as const;

const downStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;
const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    height: calc(100dvh);
    justify-content: space-between;
`;
