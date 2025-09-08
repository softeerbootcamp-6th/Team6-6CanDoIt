import { css } from '@emotion/react';
import bgImage from '../../../assets/Bg-fixed.png';
import cloudImage from '../../../assets/Bg-scroll.png';
import { useEffect, useState } from 'react';

import DetailTitle from '../../../components/molecules/Forecast/DetailTitle.tsx';
import TimeSeletor from '../../../components/organisms/Forecast/TimeSeletor.tsx';
import WeatherDetailSideBar from '../../organisms/Forecast/WeatherDetailSideBar.tsx';
import WeatherCardGroup from '../../organisms/Forecast/WeatherCardGroup.tsx';
import WeatherCardModal from '../../organisms/Forecast/WeatherSummaryCardModal.tsx';

import DownloadButton from '../../atoms/Button/DownLoadButton.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import useCourseParams from '../../../hooks/useCourseParams.ts';
import {
    getDisplayDuration,
    getRecommendComment,
    getSelectedDayStartTime,
} from './helpers.ts';
import useCourseForecast from '../../../hooks/useCourseForecast.ts';
import useSummaryInfo from '../../../hooks/useSummaryInfoSection.ts';
import ReportPendingModal from '../../molecules/Modal/ReportPendingModal.tsx';

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

    const {
        data: courseForecastData,
        isError: isCourseDataError,
        isLoading: isCourseDataLoading,
    } = useCourseForecast(selectedCourseId, scrollSelectedTime);

    const {
        data: summaryData,
        isError: isDurationError,
        isLoading: isDurationLoading,
    } = useSummaryInfo(selectedCourseId, scrollSelectedTime);
    const duration = summaryData?.duration;

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

    if (isDurationLoading || isCourseDataLoading)
        return (
            <div css={contentSectionStyles}>
                <ReportPendingModal />
            </div>
        );

    if (
        !courseForecastData ||
        !duration ||
        isCourseDataError ||
        isDurationError
    )
        return (
            <div css={contentSectionStyles}>
                <Modal onClose={() => window.location.reload()}>
                    couseData를 불러오는데 에러가 발생했습니다. 확인버튼을
                    누르면 새로고침이 진행됩니다.
                </Modal>
            </div>
        );

    const recommendComment = getRecommendComment(
        isToggleOn,
        courseForecastData,
    );
    const displayDuration = getDisplayDuration(duration);

    const {
        startCard,
        arrivalCard,
        descentCard,
        adjustedArrivalCard,
        courseAltitude,
    } = courseForecastData;

    return (
        <>
            <div css={contentSectionStyles}>
                <img src={cloudImage} css={animatedImageStyles} alt='cloud' />
                <DownloadButton onClick={() => setIsOpenCard(true)} />
                <DetailTitle
                    scrollSeletedTime={scrollSelectedTime}
                    recommendComment={recommendComment}
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
                    time={displayDuration}
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
