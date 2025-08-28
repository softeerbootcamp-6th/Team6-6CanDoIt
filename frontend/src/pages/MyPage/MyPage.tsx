import { css } from '@emotion/react';
import MyInfoSection from '../../components/templates/MyPage/MyInfoSection';
import RecentClimbSection from '../../components/templates/MyPage/RecentClimbSection.tsx';
import MyReportSection from '../../components/templates/MyPage/MyReportSection.tsx';
import MyLikeSection from '../../components/templates/MyPage/MyLikeSection.tsx';
import { useState } from 'react';
import WeatherSummaryCardModal from '../../components/templates/MyPage/WeatherSummaryCard.tsx';
import { validateAccessToken } from '../../utils/utils.ts';
import LoginRequiredModal from '../../components/molecules/Modal/LoginRequiredModal.tsx';

export default function MyPage() {
    const [isCard, setIsCard] = useState<boolean>(false);
    const [selectedCourseId, setSelectedCourseId] = useState<number | null>(
        null,
    );
    const [selectedForecastDate, setSelectedForecastDate] = useState<
        string | null
    >(null);
    const [isValidLogin, setIsValidLogin] = useState<boolean>(() =>
        validateAccessToken(),
    );

    const handleBtnClick = (courseId: number, forecastDate: string) => {
        setSelectedCourseId(courseId);
        setSelectedForecastDate(forecastDate);
        setIsCard(true);
    };

    return (
        <div css={wrapperStyles}>
            <MyInfoSection onValid={() => setIsValidLogin(true)} />
            <RecentClimbSection
                onClick={(courseId, forecastDate) =>
                    handleBtnClick(courseId, forecastDate)
                }
            />

            <MyReportSection />
            <MyLikeSection />
            {isCard && (
                <WeatherSummaryCardModal
                    onClose={() => setIsCard(false)}
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
