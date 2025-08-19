import { css } from '@emotion/react';
import LoginFormSection from '../../components/templates/Login/LoginFormSection.tsx';
import MyPageTemplate from '../../components/templates/Login/MyPageTemplate.tsx';

export default function LoginPage() {
    const hasToken =
        localStorage.getItem('accessToken') !== null ||
        sessionStorage.getItem('accessToken') !== null;

    return (
        <div css={wrapperStyles}>
            {hasToken ? <MyPageTemplate /> : <LoginFormSection />}
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 2.3rem;
    padding: 2rem;
    box-sizing: border-box;
`;
