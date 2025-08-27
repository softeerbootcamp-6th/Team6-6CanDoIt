import { css } from '@emotion/react';
import MountainInfoPreview from '../../molecules/Forecast/MountainInfoPreview.tsx';
import ScrollIndicator from '../../molecules/Forecast/ScrollIndicator.tsx';
import { summaryInfoSectionData } from '../../../constants/placeholderData.ts';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import { useSearchParams } from 'react-router-dom';
import PendingModal from '../../molecules/Modal/ReportPendingModal.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';

interface MountainCourseData {
    courseImageUrl: string;
    duration: number;
    distance: number;
    sunrise: string;
    sunset: string;
    hikingActivityStatus: HikingActivityStatus;
}

type HikingActivityStatus = '좋음' | '매우 좋음' | '나쁨' | '약간 나쁨';

const placeholderData = summaryInfoSectionData;

export default function SummaryInfoSection() {
    const [searchParams] = useSearchParams();
    const selectedCourseId = Number(searchParams.get('courseid'));

    const {
        data: summaryInfoSectionData,
        isLoading,
        isError,
    } = useApiQuery<MountainCourseData>(
        `/card/mountain/course/${selectedCourseId}`,
        { dateTime: '2025-08-22T00:00:00' },
        {
            placeholderData: placeholderData,
            retry: 3,
            enabled: true,
        },
    );

    const courseData = summaryInfoSectionData ?? placeholderData;
    const { duration, distance, courseImageUrl, sunrise, sunset } = courseData;

    return (
        <div css={wrapperStyles}>
            <MountainInfoPreview
                totalDuration={duration}
                totalDistance={distance}
                courseImageUrl={courseImageUrl}
                sunriseTime={sunrise.slice(0, 5)}
                sunsetTime={sunset.slice(0, 5)}
            />
            <ScrollIndicator />
            {isLoading && <PendingModal />}
            {isError && (
                <Modal onClose={() => window.location.reload()}>
                    산 코스 데이터를 불러오는데 에러가 발생했습니다. 새로고침을
                    통해 다시 시도해주세요.
                </Modal>
            )}
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    height: calc(100dvh - 12.5rem);
    justify-content: space-between;
    box-sizing: border-box;
    padding-bottom: 1rem;
`;
