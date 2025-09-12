import { css } from '@emotion/react';
import { useState } from 'react';
import { validateAccessToken } from '../../utils/utils.ts';

import MyInfoSection from '../../components/templates/MyPage/MyInfoSection';
import RecentClimbSection from '../../components/templates/MyPage/RecentClimbSection.tsx';
import MyReportSection from '../../components/templates/MyPage/MyReportSection.tsx';
import MyLikeSection from '../../components/templates/MyPage/MyLikeSection.tsx';
import WeatherSummaryCardModal from '../../components/templates/MyPage/WeatherSummaryCard.tsx';
import LoginRequiredModal from '../../components/molecules/Modal/LoginRequiredModal.tsx';
import useWeatherCardModal from '../../hooks/useWeatherCardModal.ts';

export default function MyPage() {
    const {
        isCardOpen,
        selectedCourseId,
        selectedForecastDate,
        openCard,
        closeCard,
    } = useWeatherCardModal();

    const [isValidLogin, setIsValidLogin] = useState<boolean>(() =>
        validateAccessToken(),
    );

    return (
        <div css={wrapperStyles}>
            <MyInfoSection onValid={() => setIsValidLogin(true)} />
            <RecentClimbSection
                onClick={(courseId, forecastDate) =>
                    openCard(courseId, forecastDate)
                }
            />

            <MyReportSection />
            <MyLikeSection />
            {isCardOpen && (
                <WeatherSummaryCardModal
                    onClose={closeCard}
                    courseId={selectedCourseId!}
                    forecastDate={selectedForecastDate!}
                />
            )}

            {!isValidLogin && (
                <LoginRequiredModal
                    onClose={() => window.location.reload()}
                    text='로그인 유효 기간이 지났습니다.'
                />
            )}
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    width: 75rem;
    margin-bottom: 2rem;
    gap: 3.5rem;
`;
