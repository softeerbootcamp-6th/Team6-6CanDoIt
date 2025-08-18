import { css } from '@emotion/react';
import RegisterHeader from '../../molecules/Register/RegisterHeader.tsx';
import RegisterForm from '../../organisms/Register/RegisterForm.tsx';
import { useRef } from 'react';
import useApiMutation from '../../../hooks/useApiMutation.ts';

interface SignUpRequest {
    nickname: string;
    loginId: string;
    password: string;
}

interface SignUpResponse {
    message: string;
}

export default function RegisterFormSection() {
    const idRef = useRef<null | HTMLInputElement>(null);
    const passwordRef = useRef<null | HTMLInputElement>(null);
    const passwordConfirmRef = useRef<null | HTMLInputElement>(null);
    const nicknameRef = useRef<null | HTMLInputElement>(null);

    const mutation = useApiMutation<SignUpRequest, SignUpResponse>(
        '/user/sign-up',
        'POST',
        {
            onSuccess: (data) => {
                if (typeof data === 'string') {
                    alert(`서버 응답: ${data}`);
                } else {
                    alert(`회원가입 성공: ${data.message}`);
                }
            },
            onError: (error: Error) => alert(`회원가입 실패: ${error.message}`),
        },
    );

    const clickRegisterHandler = () => {
        mutation.mutate({
            nickname: nicknameRef.current?.value ?? '',
            loginId: idRef.current?.value ?? '',
            password: passwordRef.current?.value ?? '',
        });
    };

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
                onClickRegister={clickRegisterHandler}
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
