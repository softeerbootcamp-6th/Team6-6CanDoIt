import WeatherCard from '../../../components/organisms/Forecast/WeatherCard.tsx';
import TimeSeletor from '../../../components/organisms/Forecast/TimeSeletor.tsx';
import DetailTitle from '../../../components/molecules/Forecast/DetailTitle.tsx';
import { css, keyframes } from '@emotion/react';
import bgImage from '../../../assets/Bg-fixed.png';
import cloudImage from '../../../assets/Bg-scroll.png';
import { WeatherIndexLight } from '../../atoms/Text/WeatherIndex.tsx';
import { useState } from 'react';
import WeatherDetailSideBar from '../../organisms/Forecast/WeatherDetailSideBar.tsx';
import { DummyWeatherData } from './dummy.ts';

const weatherDataList = [
    {
        title: '시작 지점',
        weatherInfo: {
            weatherIconName: 'rain',
            weatherIconText: '비옴',
            windSpeed: 2,
        },
    },
    {
        title: '시작 지점',
        weatherInfo: {
            weatherIconName: 'rain',
            weatherIconText: '비옴',
            windSpeed: 2,
        },
    },
    {
        title: '시작 지점',
        weatherInfo: {
            weatherIconName: 'rain',
            weatherIconText: '비옴',
            windSpeed: 2,
        },
    },
];

export default function DetailInfoSection() {
    const [isOpen, setIsOpen] = useState<boolean>(false);

    const handleCardClick = () => {
        setIsOpen((prev) => !prev);
    };

    return (
        <div css={wrapperStyles}>
            <div css={wholeWrapper}>
                <div css={contentSectionStyles}>
                    <img
                        src={cloudImage}
                        css={animatedImageStyles}
                        alt='cloud'
                    />
                    <DetailTitle />
                    <div css={weatherSummaryWrapperStyles}>
                        <div css={weatherCardWrapperStyles}>
                            {weatherDataList.map((data, index) => (
                                <WeatherCard
                                    key={index}
                                    title={data.title}
                                    weatherInfo={data.weatherInfo}
                                    onClick={handleCardClick}
                                />
                            ))}
                        </div>
                        <WeatherIndexLight type='좋음' />
                    </div>

                    <TimeSeletor />
                </div>
                {isOpen && (
                    <WeatherDetailSideBar
                        selectedWeatherData={DummyWeatherData}
                    />
                )}
            </div>
        </div>
    );
}

const float = keyframes`
    0%, 100% {
        transform: translateY(0);
    }
    50% {
        transform: translateY(-10px);
    }
`;

const animatedImageStyles = css`
    position: absolute;
    top: 0;
    width: 100%;

    height: 85%;
    opacity: 0.8;

    animation: ${float} 4s ease-in-out infinite;
    pointer-events: none;
    z-index: 0;
`;

const weatherCardWrapperStyles = css`
    display: flex;
    width: 100%;
    justify-content: space-evenly;

    & > :nth-of-type(1) {
        transform: translateY(5rem);
    }

    & > :nth-of-type(2) {
        transform: translateY(-5rem);
    }

    & > :nth-of-type(3) {
        transform: translateY(5rem);
    }
`;

const weatherSummaryWrapperStyles = css`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
`;

const wrapperStyles = css`
    width: 100%;
    padding-top: 5rem;
    box-sizing: border-box;
    height: 100dvh;
    display: flex;
    flex-direction: column;
`;

const contentSectionStyles = css`
    height: 100%;
    position: relative;
    display: flex;
    flex: 1 1 auto;
    margin-top: 5rem;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    margin: auto;
    padding: 2rem 0;
    box-sizing: border-box;
    width: 75%;
    max-width: 80%;
    background-image: url(${bgImage});
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    & h1 {
        text-align: center;
    }
`;

const wholeWrapper = css`
    height: 100%;
    display: flex;
`;
