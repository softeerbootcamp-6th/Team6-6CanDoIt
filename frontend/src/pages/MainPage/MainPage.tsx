import MountainCard from '../../components/organisms/Main/MountainCard.tsx';
import { css } from '@emotion/react';
import SearchBar from '../../components/organisms/Common/SearchBar.tsx';
import { DisplayHeading } from '../../components/atoms/Heading/Heading.tsx';

export default function MainPage() {
    const handleWheel = (event: React.WheelEvent<HTMLDivElement>) => {
        event.preventDefault();
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };

    const isOpen = false;

    return (
        <>
            {isOpen && <div css={backgroundStyle} />}
            <div css={overBackgroundStyle}>
                <DisplayHeading HeadingTag='h1'>Header</DisplayHeading>
                <SearchBar {...searchBarProps} />
            </div>
            <div css={mountainCardContainerStyle} onWheel={handleWheel}>
                {data.map((mountain) => (
                    <MountainCard {...mountain} />
                ))}
            </div>
        </>
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

const backgroundStyle = css`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.7;
    background: linear-gradient(180deg, #000 0%, rgba(0, 0, 0, 0) 100%);
`;

const overBackgroundStyle = css`
    position: relative;
    z-index: 10;

    display: flex;
    flex-direction: column;
    gap: 2rem;

    margin-bottom: 2rem;
`;

const mountainCardContainerStyle = css`
    display: flex;
    flex-direction: row;
    gap: 1rem;
    overflow-x: auto;
    padding: 1.5rem 2rem;
    width: 100%;
    box-sizing: border-box;

    scrollbar-width: none;
    -ms-overflow-style: none;

    &::-webkit-scrollbar {
        display: none;
    }
`;

const data = [
    {
        mountainName: '설악산',
        weatherIconName: 'clear-day',
        surfaceTemperature: 20,
        summitTemperature: 15,
    },
    {
        mountainName: '한라산',
        weatherIconName: 'partly-cloudy-day',
        surfaceTemperature: 25,
        summitTemperature: 20,
    },
    {
        mountainName: '지리산',
        weatherIconName: 'rain',
        surfaceTemperature: 22,
        summitTemperature: 18,
    },
    {
        mountainName: '오대산',
        weatherIconName: 'snow',
        surfaceTemperature: 19,
        summitTemperature: 14,
    },
    {
        mountainName: '태백산',
        weatherIconName: 'thunderstorm',
        surfaceTemperature: 21,
        summitTemperature: 16,
    },
    {
        mountainName: '덕유산',
        weatherIconName: 'yellow-dust',
        surfaceTemperature: 23,
        summitTemperature: 17,
    },
];
