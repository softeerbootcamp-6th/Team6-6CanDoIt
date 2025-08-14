import { css } from '@emotion/react';
import LoginFormSection from '../../components/templates/Login/LoginFormSection.tsx';

export default function LoginPage() {
    return (
        <div css={wrapperStyles}>
            <LoginFormSection />
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
