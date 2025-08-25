import { css } from '@emotion/react';
import RegisterFormSection from '../../components/templates/Register/RegisterFormSection';

export default function RegisterPage() {
    return (
        <div css={wrapperStyles}>
            <RegisterFormSection />
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
