import { css } from '@emotion/react';
import bgImage from '../../../assets/Bg-fixed.png';
import cloudImage from '../../../assets/Bg-scroll.png';
import { keepPreviousData } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

import useApiQuery from '../../../hooks/useApiQuery.ts';
import { useForecastCardData } from '../../../hooks/useForecastCardData.ts';

import Icon from '../../atoms/Icon/Icons.tsx';
import DetailTitle from '../../../components/molecules/Forecast/DetailTitle.tsx';
import TimeSeletor from '../../../components/organisms/Forecast/TimeSeletor.tsx';
import WeatherDetailSideBar from '../../organisms/Forecast/WeatherDetailSideBar.tsx';
import WeatherCardGroup from '../../organisms/Forecast/WeatherCardGroup.tsx';
import WeatherCardModal from '../../organisms/Forecast/WeatherSummaryCardModal.tsx';

import { theme } from '../../../theme/theme.ts';
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

type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';
type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';

export default function DetailInfoSection() {
    const [searchParams] = useSearchParams();
    const selectedCourseId = Number(searchParams.get('courseid'));
    const selectedMountainId = Number(searchParams.get('mountainid'));
    const selectedWeekdayId = Number(searchParams.get('weekdayid'));

    const [isOpen, setIsOpen] = useState<boolean>(false);
    const [isCard, setIsCard] = useState<boolean>(false);
    const [isToggleOn, setIsToggleOn] = useState<boolean>(false);
    const [sidebarData, setSidebarData] = useState<{
        backgroundType: Background;
        title: string;
        courseAltitude?: number;
        data: CardData;
    } | null>(null);
    const [scrollSelectedTime, setScrollSelectedTime] = useState<string>(
        getDayStartTime(selectedWeekdayId),
    );

    useEffect(() => {
        setScrollSelectedTime(getDayStartTime(selectedWeekdayId));
    }, [selectedWeekdayId]);

    const { frontCard, backCard, refetch } = useForecastCardData(
        selectedCourseId,
        scrollSelectedTime,
    );

    const cardData = { frontCard, backCard };

    const { data: courseForecastData = detailInfoSectionData } =
        useApiQuery<CourseForcast>(
            `/card/course/${selectedCourseId}/forecast`,
            { startDateTime: scrollSelectedTime },
            {
                placeholderData: keepPreviousData,
                enabled: true,
            },
        );

    const { data: summaryInfoData = summaryInfoSectionData } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: scrollSelectedTime },
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

    useEffect(() => {
        setIsOpen(false);
    }, [scrollSelectedTime, isToggleOn]);

    const openSidebar = (
        backgroundType: Background,
        title: string,
        courseAltitude: number | undefined,
        data: CardData,
    ) => {
        setSidebarData({ backgroundType, title, courseAltitude, data });
        setIsOpen(true);
    };

    const closeSidebar = () => {
        setIsOpen(false);
    };

    const handleDownloadBtnClick = async () => {
        await refetch();
        setIsCard(true);
    };

    return (
        <>
            <div css={contentSectionStyles}>
                <button css={storeBtnStyles} onClick={handleDownloadBtnClick}>
                    <Icon {...downloadIconProps} />
                </button>
                <img src={cloudImage} css={animatedImageStyles} alt='cloud' />
                <DetailTitle
                    scrollSeletedTime={scrollSelectedTime}
                    recommendComment={
                        isToggleOn ? adjustedRecommendComment : recommendComment
                    }
                />
                <WeatherCardGroup
                    weatherDataObjs={{
                        startCard,
                        arrivalCard,
                        descentCard,
                        adjustedArrivalCard,
                    }}
                    isToggleOn={isToggleOn}
                    courseAltitude={courseAltitude}
                    onSidebar={(
                        backgroundType: Background,
                        title: string,
                        courseAltitude: number,
                        data: CardData,
                    ) =>
                        openSidebar(backgroundType, title, courseAltitude, data)
                    }
                />

                <TimeSeletor
                    scrollSelectedTime={scrollSelectedTime}
                    onToggle={() => setIsToggleOn((prev) => !prev)}
                    isToggleOn={isToggleOn}
                    time={Math.ceil(duration)}
                    selectedMountainId={selectedMountainId}
                    onTimeSelect={(time) => setScrollSelectedTime(time)}
                />
            </div>
            {isOpen && (
                <WeatherDetailSideBar
                    courseAltitude={sidebarData?.courseAltitude}
                    type={sidebarData?.title!}
                    onClose={closeSidebar}
                    card={sidebarData}
                />
            )}
            {isCard && (
                <WeatherCardModal
                    cardData={cardData}
                    onClose={() => setIsCard(false)}
                    scrollSelectedDate={scrollSelectedTime}
                    selectedCourseId={selectedCourseId}
                />
            )}
        </>
    );
}

function getDayStartTime(dayOffset: number): string {
    const now = new Date();
    const targetDate = new Date(
        now.getFullYear(),
        now.getMonth(),
        now.getDate() + (dayOffset - 1),
    );

    const year = targetDate.getFullYear();
    const month = (targetDate.getMonth() + 1).toString().padStart(2, '0');
    const day = targetDate.getDate().toString().padStart(2, '0');

    const hour =
        dayOffset === 1 ? now.getHours().toString().padStart(2, '0') : '00';

    return `${year}-${month}-${day}T${hour}:00:00`;
}

const downloadIconProps = {
    name: 'download-02',
    width: 1.4,
    height: 1.4,
    color: 'grey-100',
};

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
