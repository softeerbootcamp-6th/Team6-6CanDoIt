import { css } from '@emotion/react';
import MyInfoSection from '../../components/templates/MyPage/MyInfoSection';
import { userInfoData } from '../../constants/placeholderData.ts';
import RecentClimbSection from '../../components/templates/MyPage/RecentClimbSection.tsx';

const placeholderUserData = userInfoData;

export default function MyPage() {
    return (
        <div css={wrapperStyles}>
            <MyInfoSection userData={placeholderUserData} />
            <RecentClimbSection />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    min-width: 75rem;
    margin: auto;
    gap: 3.5rem;
`;
