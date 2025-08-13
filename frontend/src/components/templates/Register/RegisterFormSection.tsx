import { css } from '@emotion/react';
import RegisterHeader from '../../molecules/Register/RegisterHeader.tsx';
import RegisterForm from '../../organisms/Register/RegisterForm.tsx';

export default function RegisterFormSection() {
    return (
        <div css={wrapperStyles}>
            <RegisterHeader />
            <RegisterForm />
        </div>
    );
}

const wrapperStyles = css`
    width: 29rem;
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
`;
