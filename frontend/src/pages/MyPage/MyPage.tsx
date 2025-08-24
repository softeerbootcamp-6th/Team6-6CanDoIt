import { css } from '@emotion/react';
import MyInfoSection from '../../components/templates/MyPage/MyInfoSection';
import { userInfoData } from '../../constants/placeholderData.ts';
import RecentClimbSection from '../../components/templates/MyPage/RecentClimbSection.tsx';
import MyReportSection from '../../components/templates/MyPage/MyReportSection.tsx';
import MyLikeSection from '../../components/templates/MyPage/MyLikeSection.tsx';

const placeholderUserData = userInfoData;

export default function MyPage() {
    return (
        <div css={wrapperStyles}>
            <MyInfoSection userData={placeholderUserData} />
            <RecentClimbSection />
            <MyReportSection />
            <MyLikeSection />
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
