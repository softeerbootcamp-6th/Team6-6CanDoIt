import { css } from '@emotion/react';
import MyInfoSection from '../../components/templates/MyPage/MyInfoSection';
import { userInfoData } from '../../constants/placeholderData.ts';

const placeholderUserData = userInfoData;

export default function MyPage() {
    return (
        <div css={wrapperStyles}>
            <MyInfoSection userData={placeholderUserData} />
        </div>
    );
}

const wrapperStyles = css`
    width: 75rem;
    margin: auto;
`;
