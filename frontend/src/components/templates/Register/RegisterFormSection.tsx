import { css } from '@emotion/react';
import RegisterHeader from '../../molecules/Register/RegisterHeader.tsx';
import RegisterForm from '../../organisms/Register/RegisterForm.tsx';
import { useRef } from 'react';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import { validateRegisterInput } from './utils.ts';
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

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
    const checkBoxValid = useRef<boolean>(false);

    const confirmedIdRef = useRef('');
    const confirmedNicknameRef = useRef('');

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
        const loginId = idRef.current?.value ?? '';
        const nickname = nicknameRef.current?.value ?? '';
        const password = passwordRef.current?.value ?? '';
        const passwordConfirm = passwordConfirmRef.current?.value ?? '';
        const checkBox = checkBoxValid.current;
        const errors = validateRegisterInput({
            loginId,
            confirmedId: confirmedIdRef.current,
            nickname,
            confirmedNickname: confirmedNicknameRef.current,
            password,
            passwordConfirm,
            checkBox,
        });

        if (errors.length > 0) {
            alert(errors.join('\n'));
            return;
        }

        mutation.mutate({
            nickname: nicknameRef.current?.value ?? '',
            loginId: idRef.current?.value ?? '',
            password: passwordRef.current?.value ?? '',
        });
    };

    const clickCheckIdHandler = async () => {
        const loginId = idRef.current?.value ?? '';

        try {
            const res = await fetch(
                `${API_BASE_URL}/user/login-id?loginId=${encodeURIComponent(loginId)}`,
            );

            if (!res.ok) {
                const text = await res.text();
                try {
                    const errorData = JSON.parse(text);
                    alert(`사용 불가: ${errorData.message}`);
                } catch {
                    alert(`아이디 확인 실패: ${text}`);
                }
                return;
            }
            confirmedIdRef.current = idRef.current?.value ?? '';
        } catch (err) {
            alert(`아이디 확인 실패: ${(err as Error).message}`);
        }
    };

    const clickCheckNicknameHandler = async () => {
        const nickname = nicknameRef.current?.value ?? '';

        try {
            const res = await fetch(
                `${API_BASE_URL}/user/nickname?nickname=${encodeURIComponent(nickname)}`,
            );

            if (!res.ok) {
                const text = await res.text();
                try {
                    const errorData = JSON.parse(text);
                    alert(`사용 불가: ${errorData.message}`);
                } catch {
                    alert(`닉네임 확인 실패: ${text}`);
                }
                return;
            }
            confirmedNicknameRef.current = nicknameRef.current?.value ?? '';
        } catch (err) {
            alert(`닉네임 확인 실패: ${(err as Error).message}`);
        }
    };

    const checkCheckBoxValid = (isValid: boolean) => {
        checkBoxValid.current = isValid;
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
                onClickCheckId={clickCheckIdHandler}
                onClickCheckNickName={clickCheckNicknameHandler}
                onCheckStatusChange={(isValid: boolean) =>
                    checkCheckBoxValid(isValid)
                }
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
