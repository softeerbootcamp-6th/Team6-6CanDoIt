import { css } from '@emotion/react';
import MyInfoSection from '../../components/templates/MyPage/MyInfoSection';

export default function MyPage() {
    return (
        <div css={wrapperStyles}>
            <MyInfoSection />
        </div>
    );
}

const wrapperStyles = css`
    width: 75rem;
    margin: auto;
`;
