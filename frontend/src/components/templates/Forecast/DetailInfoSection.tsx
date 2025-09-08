import { css } from '@emotion/react';
import bgImage from '../../../assets/Bg-fixed.png';
import cloudImage from '../../../assets/Bg-scroll.png';
import { keepPreviousData } from '@tanstack/react-query';
import { useEffect, useState } from 'react';

import useApiQuery from '../../../hooks/useApiQuery.ts';

import DetailTitle from '../../../components/molecules/Forecast/DetailTitle.tsx';
import TimeSeletor from '../../../components/organisms/Forecast/TimeSeletor.tsx';
import WeatherDetailSideBar from '../../organisms/Forecast/WeatherDetailSideBar.tsx';
import WeatherCardGroup from '../../organisms/Forecast/WeatherCardGroup.tsx';
import WeatherCardModal from '../../organisms/Forecast/WeatherSummaryCardModal.tsx';

import { detailInfoSectionData } from '../../../constants/placeholderData.ts';
import DownloadButton from '../../atoms/Button/DownLoadButton.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import useCourseParams from '../../../hooks/useCourseParams.ts';
import { getSelectedDayStartTime } from './helpers.ts';

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

interface SideBarProps {
    backgroundType: Background;
    title: string;
    courseAltitude?: number;
    data: CardData;
}

type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';
type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';

export default function DetailInfoSection() {
    const { selectedCourseId, selectedMountainId, selectedWeekdayId } =
        useCourseParams();

    const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(false);
    const [isOpenCard, setIsOpenCard] = useState<boolean>(false);
    const [isToggleOn, setIsToggleOn] = useState<boolean>(false);

    const [sidebarData, setSidebarData] = useState<SideBarProps | null>(null);

    const [scrollSelectedTime, setScrollSelectedTime] = useState<string>(
        getSelectedDayStartTime(selectedWeekdayId),
    );

    useEffect(() => {
        setScrollSelectedTime(getSelectedDayStartTime(selectedWeekdayId));
    }, [selectedWeekdayId]);

    const {
        data: courseForecastData = detailInfoSectionData,
        isError: isCourseDataError,
    } = useApiQuery<CourseForcast>(
        `/card/course/${selectedCourseId}/forecast`,
        { startDateTime: scrollSelectedTime },
        {
            placeholderData: keepPreviousData,
            retry: 3,
            enabled: true,
        },
    );

    const { data: duration = 0, isError: isDurationError } = useApiQuery<any>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: scrollSelectedTime },
        {
            placeholderData: keepPreviousData,
            retry: 3,
            enabled: true,
            select: (data) => data.duration,
        },
    );

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
        setIsSidebarOpen(false);
    }, [scrollSelectedTime, isToggleOn]);

    const openSidebar = (
        backgroundType: Background,
        title: string,
        courseAltitude: number | undefined,
        data: CardData,
    ) => {
        setSidebarData({ backgroundType, title, courseAltitude, data });
        setIsSidebarOpen(true);
    };

    return (
        <>
            <div css={contentSectionStyles}>
                <img src={cloudImage} css={animatedImageStyles} alt='cloud' />
                <DownloadButton onClick={() => setIsOpenCard(true)} />
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
                    time={isDurationError ? Math.ceil(duration) : 3}
                    selectedMountainId={selectedMountainId}
                    onTimeSelect={(time) => setScrollSelectedTime(time)}
                />
            </div>
            {isSidebarOpen && (
                <WeatherDetailSideBar
                    courseAltitude={sidebarData?.courseAltitude}
                    type={sidebarData?.title!}
                    onClose={() => setIsSidebarOpen(false)}
                    card={sidebarData}
                />
            )}
            {isOpenCard && (
                <WeatherCardModal
                    onClose={() => setIsOpenCard(false)}
                    scrollSelectedTime={scrollSelectedTime}
                    selectedCourseId={selectedCourseId}
                />
            )}
            {isCourseDataError && (
                <Modal onClose={() => window.location.reload()}>
                    couseData를 불러오는데 에러가 발생했습니다. 새로고침을 통해
                    다시 시도해주세요.
                </Modal>
            )}
        </>
    );
}

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
