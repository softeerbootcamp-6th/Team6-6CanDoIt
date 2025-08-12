import { css } from '@emotion/react';
import LoginTemplate from '../../components/templates/Login/LoginTemplate';
import RegisterTemplate from '../../components/templates/Login/RegisterTemplate';

export default function LoginPage() {
    return (
        <div css={wrapperStyles}>
            <LoginTemplate />
            <RegisterTemplate />
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
