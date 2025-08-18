import { css } from '@emotion/react';
import RegisterHeader from '../../molecules/Register/RegisterHeader.tsx';
import RegisterForm from '../../organisms/Register/RegisterForm.tsx';
import { useRef } from 'react';

export default function RegisterFormSection() {
    const idRef = useRef<null | HTMLInputElement>(null);
    const passwordRef = useRef<null | HTMLInputElement>(null);
    const passwordConfirmRef = useRef<null | HTMLInputElement>(null);
    const nicknameRef = useRef<null | HTMLInputElement>(null);

    return (
        <div css={wrapperStyles}>
            <RegisterHeader />
            <RegisterForm
                refs={{
                    idRef,
                    passwordRef,
                    passwordConfirmRef,
                    nicknameRef,
                }}
            />
        </div>
    );
}

const wrapperStyles = css`
    width: 29rem;
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
`;
