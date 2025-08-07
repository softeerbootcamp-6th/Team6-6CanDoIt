import WeatherCard from '../../components/organisms/WeatherCard/WeatherCard';
import TimeSeletor from '../../components/organisms/TimeSeletor/TimeSeletor';
import { DisplayHeading } from '../../components/atoms/Heading/Heading';
import CommonText from '../../components/atoms/Text/CommonText';
import { css, keyframes } from '@emotion/react';
import bgImage from '../../assets/Bg-fixed.png';
import cloudImage from '../../assets/Bg-scroll.png';
import Header from '../../components/organisms/Header/Header';

export default function ForecastPage() {
    return (
        <div css={wrapperStyles}>
            <Header />
            <div css={contentSectionStyles}>
                <img src={cloudImage} css={animatedImageStyles} alt='cloud' />
                <DisplayHeading HeadingTag='h1'>
                    <span css={displayHeadingStyle}>
                        오전 9시
                        <CommonText
                            TextTag='span'
                            fontSize='display'
                            fontWeight='regular'
                            color='greyOpacityWhite-40'
                        >
                            에 출발하면
                        </CommonText>
                    </span>
                    <span css={lineGroupStyle}>바람막이가 필요할 거에요</span>
                </DisplayHeading>
                <div css={weatherCardWrapperStyles}>
                    <WeatherCard
                        title='시작 지점'
                        weatherInfo={{
                            weatherIconName: 'rain',
                            weatherIconText: '비옴',
                            windSpeed: 2,
                        }}
                    />
                    <WeatherCard
                        title='시작 지점'
                        weatherInfo={{
                            weatherIconName: 'rain',
                            weatherIconText: '비옴',
                            windSpeed: 2,
                        }}
                    />
                    <WeatherCard
                        title='시작 지점'
                        weatherInfo={{
                            weatherIconName: 'rain',
                            weatherIconText: '비옴',
                            windSpeed: 2,
                        }}
                    />
                </div>
                <TimeSeletor />
            </div>
        </div>
    );
}

const wrapperStyles = css`
    width: 100%;
    height: 100dvh;
    display: flex;
    flex-direction: column;
`;

const contentSectionStyles = css`
    position: relative;
    display: flex;
    flex: 1 1 auto;

    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    margin: auto;
    width: 80%;
    background-image: url(${bgImage});
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    padding-bottom: 2rem;
    & h1 {
        text-align: center;
    }
`;

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
    height: 95%;
    opacity: 0.8;

    animation: ${float} 4s ease-in-out infinite;
    pointer-events: none;
    z-index: 0;
`;

const displayHeadingStyle = css`
    line-height: 1.5;
    white-space: normal;
    margin-bottom: 1rem;
`;

const lineGroupStyle = css`
    display: block;
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
