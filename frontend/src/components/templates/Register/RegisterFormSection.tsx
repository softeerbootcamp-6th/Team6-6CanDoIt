import { css } from '@emotion/react';
import RegisterHeader from '../../molecules/Register/RegisterHeader.tsx';
import RegisterForm from '../../organisms/Register/RegisterForm.tsx';
import { useRef, useState } from 'react';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import { validateRegisterInput } from './utils.ts';
import { useNavigate } from 'react-router-dom';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import PendingModal from '../../molecules/Modal/ReportPendingModal.tsx';
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
    const [modalMessage, setModalMessage] = useState<string>('');
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const idRef = useRef<null | HTMLInputElement>(null);
    const passwordRef = useRef<null | HTMLInputElement>(null);
    const passwordConfirmRef = useRef<null | HTMLInputElement>(null);
    const nicknameRef = useRef<null | HTMLInputElement>(null);
    const checkBoxValid = useRef<boolean>(false);

    const confirmedIdRef = useRef('');
    const confirmedNicknameRef = useRef('');

    const navigate = useNavigate();

    const mutation = useApiMutation<SignUpRequest, SignUpResponse>(
        '/user/sign-up',
        'POST',
        {
            onSuccess: () => {
                setModalMessage('회원가입 되셨습니다.');
            },
            onError: (error: Error) => setModalMessage(error.message),
            onMutate: () => setIsLoading(true),
            onSettled: () => setIsLoading(false),
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
            setModalMessage(errors);
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
                    setModalMessage(errorData.message);
                } catch {
                    setModalMessage(text);
                }
                return;
            }
            setModalMessage('id가 중복되지 않습니다.');
            confirmedIdRef.current = idRef.current?.value ?? '';
        } catch (err) {
            setModalMessage((err as Error).message);
            return;
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
                    setModalMessage(errorData.message);
                } catch {
                    setModalMessage(text);
                }
                return;
            }
            setModalMessage('닉네임이 중복되지 않습니다.');
            confirmedNicknameRef.current = nicknameRef.current?.value ?? '';
        } catch (err) {
            setModalMessage((err as Error).message);
            return;
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
            {modalMessage && (
                <Modal
                    onClose={
                        mutation.isSuccess
                            ? () => navigate('/login')
                            : () => setModalMessage('')
                    }
                >
                    <div css={preStyles}>{modalMessage}</div>
                </Modal>
            )}
            {isLoading && <PendingModal />}
        </div>
    );
}

const wrapperStyles = css`
    width: 29rem;
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
`;

const preStyles = css`
    display: block;
    white-space: pre-wrap;
    line-height: 1.5;
    text-align: center;
`;
