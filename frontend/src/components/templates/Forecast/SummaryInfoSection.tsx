import { css } from '@emotion/react';
import MountainInfoPreview from '../../molecules/Forecast/MountainInfoPreview.tsx';
import ScrollIndicator from '../../molecules/Forecast/ScrollIndicator.tsx';
import PendingModal from '../../molecules/Modal/ReportPendingModal.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';

import useCourseParams from '../../../hooks/useCourseParams.ts';
import useSummaryInfo from '../../../hooks/useSummaryInfoSection.ts';

import { getSelectedDayStartTime } from './helpers.ts';

export default function SummaryInfoSection() {
    const { selectedCourseId, selectedWeekdayId } = useCourseParams();

    const {
        data: summaryInfoSectionData,
        isLoading,
        isError,
    } = useSummaryInfo(
        selectedCourseId,
        getSelectedDayStartTime(selectedWeekdayId),
    );

    if (isLoading) {
        return (
            <div css={wrapperStyles}>
                <PendingModal />
            </div>
        );
    }

    if (isError || !summaryInfoSectionData) {
        return (
            <div css={wrapperStyles}>
                <Modal onClose={() => window.location.reload()}>
                    산 코스 데이터를 불러오는데 에러가 발생했습니다. 확인 버튼을
                    눌러 다시 시도해주세요.
                </Modal>
            </div>
        );
    }

    const { duration, distance, courseImageUrl, sunrise, sunset } = summaryInfoSectionData;

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
