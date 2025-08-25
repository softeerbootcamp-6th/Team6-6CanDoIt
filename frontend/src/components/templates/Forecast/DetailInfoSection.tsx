import WeatherCard from '../../../components/organisms/Forecast/WeatherCard.tsx';
import TimeSeletor from '../../../components/organisms/Forecast/TimeSeletor.tsx';
import DetailTitle from '../../../components/molecules/Forecast/DetailTitle.tsx';
import { css } from '@emotion/react';
import bgImage from '../../../assets/Bg-fixed.png';
import svg from '../../../assets/line.svg';
import cloudImage from '../../../assets/Bg-scroll.png';
import { WeatherIndexLight } from '../../atoms/Text/WeatherIndex.tsx';
import { useState } from 'react';
import WeatherDetailSideBar from '../../organisms/Forecast/WeatherDetailSideBar.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import { theme } from '../../../theme/theme.ts';
import WeatherCardModal from '../../organisms/Forecast/WeatherSummaryCardModal.tsx';
import { useSearchParams } from 'react-router-dom';
import { useForecastCardData } from '../../../hooks/useForecastCardData.ts';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import { keepPreviousData } from '@tanstack/react-query';
import {
    detailInfoSectionData,
    summaryInfoSectionData,
} from '../../../constants/placeholderData.ts';

interface CourseForcast {
    startCard: CardData;
    arrivalCard: CardData;
    adjustedArrivalCard: CardData;
    descentCard: CardData;
    courseAltitude: number;
    recommendComment: string;
    adjustedRecommendComment: string;
}

interface CardData {
    dateTime: string;
    hikingActivity: HikingActivityStatus;
    temperature: number;
    apparentTemperature: number;
    temperatureDescription: string;
    precipitation: string;
    probabilityDescription: string;
    precipitationType: string;
    sky: string;
    skyDescription: string;
    windSpeed: number;
    windSpeedDescription: string;
    humidity: number;
    humidityDescription: string;
    highestTemperature: number;
    lowestTemperature: number;
    title?: string;
}

type HikingActivityStatus = '좋음' | '매우좋음' | '나쁨' | '보통';
type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';

export default function DetailInfoSection() {
    const [searchParams] = useSearchParams();
    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [isCard, setIsCard] = useState<boolean>(false);
    const [isToggleOn, setIsToggleOn] = useState<boolean>(false);
    const [sidebarData, setSidebarData] = useState<{
        backgroundType: Background;
        title: string;
        courseAltitude?: number;
    } | null>(null);
    const [scrollSeletedTime, setScrollSelectedTime] = useState<string>(
        '2025-08-22T00:00:00',
    );
    const seletedTime = '2025-08-22T00:00:00';
    const selectedCourseId = Number(searchParams.get('courseid'));
    const selectedMountainId = Number(searchParams.get('mountainid'));

    const { frontCard, backCard, refetch } = useForecastCardData(
        selectedCourseId,
        scrollSeletedTime,
    );

    const cardData = { frontCard, backCard };
    const { data: courseForecastData = detailInfoSectionData } =
        useApiQuery<CourseForcast>(
            `/card/course/${selectedCourseId}/forecast`,
            { startDateTime: scrollSeletedTime },
            {
                placeholderData: keepPreviousData,
                enabled: true,
            },
        );

    const { data: summaryInfoData = summaryInfoSectionData } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: scrollSeletedTime },
        {
            retry: false,
            placeholderData: keepPreviousData,
        },
    );

    const { duration } = summaryInfoData;

    const {
        startCard,
        arrivalCard,
        descentCard,
        adjustedArrivalCard,
        recommendComment,
        adjustedRecommendComment,
        courseAltitude,
    } = courseForecastData;

    const selectedArrivalCard = isToggleOn ? adjustedArrivalCard : arrivalCard;
    startCard.title = '시작지점';
    descentCard.title = '끝지점';
    selectedArrivalCard.title = '최고점';

    const weatherDataList = [
        startCard,
        isToggleOn ? adjustedArrivalCard : arrivalCard,
        descentCard,
    ];

    const handleCardClick = (
        backgroundType: Background,
        title: string,
        courseAltitude: number | undefined,
    ) => {
        setSidebarData({ backgroundType, title, courseAltitude });
        setIsOpen(true);
    };

    const handleSidebarClose = () => {
        setIsOpen(false);
    };

    const getForcastCardData = async () => {
        await refetch();
    };

    function findSidebarCard(type: string, weatherDataList: any) {
        if (type == '시작지점') return weatherDataList[0];
        else if (type == '끝지점') return weatherDataList[2];
        else return weatherDataList[1];
    }

    return (
        <div css={wrapperStyles}>
            <div css={wholeWrapper}>
                <div css={contentSectionStyles}>
                    <button
                        css={storeBtnStyles}
                        onClick={() => {
                            setIsCard(true);
                            getForcastCardData();
                        }}
                    >
                        <Icon
                            name='download-02'
                            width={1.4}
                            height={1.4}
                            color='grey-100'
                        />
                    </button>
                    <img
                        src={cloudImage}
                        css={animatedImageStyles}
                        alt='cloud'
                    />
                    <DetailTitle
                        scrollSeletedTime={scrollSeletedTime}
                        recommendComment={
                            isToggleOn
                                ? adjustedRecommendComment
                                : recommendComment
                        }
                    />
                    <div css={weatherSummaryWrapperStyles}>
                        <div css={weatherCardWrapperStyles}>
                            {weatherDataList.map((data, index) => {
                                const {
                                    sky,
                                    windSpeed,
                                    skyDescription,
                                    temperature,
                                    precipitationType,
                                } = data;
                                return (
                                    <WeatherCard
                                        key={index}
                                        title={data.title!}
                                        weatherIconName={sky}
                                        courseAltitude={
                                            index === 1
                                                ? courseAltitude
                                                : undefined
                                        }
                                        weatherIconText={skyDescription}
                                        windSpeed={windSpeed}
                                        temperature={temperature}
                                        precipitationType={precipitationType}
                                        onClick={(
                                            backgroundType,
                                            title,
                                            courseAltitude,
                                        ) =>
                                            handleCardClick(
                                                backgroundType,
                                                title,
                                                courseAltitude,
                                            )
                                        }
                                    />
                                );
                            })}
                        </div>
                        <img css={lineImageStyles} src={svg}></img>
                        <WeatherIndexLight type='좋음' />
                    </div>

                    <TimeSeletor
                        scrollSelectedTime={scrollSeletedTime}
                        onToggle={() => setIsToggleOn((prev) => !prev)}
                        isToggleOn={isToggleOn}
                        time={Math.ceil(duration)}
                        selectedTime={seletedTime}
                        selectedMountainId={selectedMountainId}
                        onTimeSelect={(time) => setScrollSelectedTime(time)}
                    />
                </div>
                {isOpen && (
                    <WeatherDetailSideBar
                        courseAltitude={sidebarData?.courseAltitude}
                        type={sidebarData?.title!}
                        onClose={handleSidebarClose}
                        card={findSidebarCard(
                            sidebarData?.title!,
                            weatherDataList,
                        )}
                    />
                )}
                {isCard && (
                    <WeatherCardModal
                        cardData={cardData}
                        onClose={() => setIsCard(false)}
                    />
                )}
            </div>
        </div>
    );
}

const { colors } = theme;

const animatedImageStyles = css`
    position: absolute;
    top: 0;
    width: 100%;

    height: 85%;
    opacity: 0.8;

    pointer-events: none;
    z-index: 0;
`;

const weatherCardWrapperStyles = css`
    display: flex;
    width: 100%;
    justify-content: space-evenly;

    & > :nth-of-type(1) {
        transform: translateY(5.5rem);
    }

    & > :nth-of-type(2) {
        transform: translateY(-4.5rem);
    }

    & > :nth-of-type(3) {
        transform: translateY(5.5rem);
    }
`;

const weatherSummaryWrapperStyles = css`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
`;

const wrapperStyles = css`
    position: relative;
    z-index: 100;
    background-color: ${colors.grey[0]};
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
    width: 70%;
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
    position: relative;
`;

const storeBtnStyles = css`
    all: unset;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    top: 3rem;
    right: 5%;
    width: 3rem;
    height: 3rem;
    border-radius: 100%;
    background-color: ${colors.greyOpacityWhite[70]};
    padding: 0 3px 5px 0;
    box-sizing: border-box;
    cursor: pointer;
`;

const lineImageStyles = css`
    position: absolute;
    top: 35%;
    z-index: -1;
    width: 60%;
    height: 32%;
`;
