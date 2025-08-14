import { css } from '@emotion/react';
import MountainInfoPreview from '../molecules/Forecast/MountainInfoPreview.tsx';
import { WeatherIndexLight } from '../atoms/Text/WeatherIndex.tsx';
import CommonText from '../atoms/Text/CommonText';
import Icon from '../atoms/Icon/Icons';
import SearchBar from '../organisms/Common/SearchBar.tsx';

export default function ForecastMain() {
    return (
        <div css={wrapperStyles}>
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

const mountainData = ['설악산', '한라산', '지리산'];
const courseData = ['코스1', '코스2', '코스3'];

const searchBarProps = {
    searchBarTitle: '어디 날씨를 확인해볼까요?',
    searchBarMessage: '를 오르는',
    pageName: 'main',
    mountainData,
    courseData,
} as const;

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
