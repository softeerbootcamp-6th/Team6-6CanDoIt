import { css } from '@emotion/react';
import { useRef } from 'react';
import FormButton from '../../atoms/Button/FormButton.tsx';
import CheckBox from '../../atoms/Form/CheckBox.tsx';
import TextInputWithIcon from '../../molecules/Input/TextInputWithIcon.tsx';
import type { ColorValueType } from '../../../types/themeTypes';
import { iconButtonHandler } from '../Register/utils.ts';
import useApiMutation from '../../../hooks/useApiMutation.ts';

export default function LoginForm() {
    const idRef = useRef<null | HTMLInputElement>(null);
    const passwordRef = useRef<null | HTMLInputElement>(null);

    const mutation = useApiMutation<
        { loginId: string; password: string },
        { message: string }
    >('/user/sign-in', 'POST', {
        onSuccess: (data) => {
            if (typeof data === 'string') {
                alert(`서버 응답: ${data}`);
            } else {
                alert(`회원가입 성공: ${data.message}`);
            }
        },
        onError: (error: Error) => alert(`로그인 실패: ${error.message}`),
    });

    const clickSignInHandler = () => {
        mutation.mutate({
            loginId: idRef.current?.value ?? '',
            password: passwordRef.current?.value ?? '',
        });
    };

    const inputFieldsWithRef = [
        { ...inputFields[0], inputRef: idRef },
        { ...inputFields[1], inputRef: passwordRef },
    ];

    return (
        <form>
            <div css={InputWrapperStyles}>
                {inputFieldsWithRef.map(({ ...field }) => (
                    <TextInputWithIcon
                        key={field.id}
                        id={field.id}
                        icon={field.icon}
                        label={field.label}
                        type={field.type}
                        onIconClick={field.onIconClick}
                        iconAriaLabel={field.iconAriaLabel}
                        inputRef={field.inputRef}
                    />
                ))}
            </div>
            <CheckBox
                id='login-button'
                text='자동 로그인'
                grey={60 as ColorValueType}
            />
            <FormButton
                text='로그인'
                onClick={clickSignInHandler}
                margin='2.5rem 0 0 0'
            />
        </form>
    );
}

const inputFields = [
    {
        id: 'email-input',
        icon: 'x-circle',
        label: '이메일 주소',
        type: 'text',
        onIconClick: iconButtonHandler.clearTextByRef,
        iconAriaLabel: '이메일 지우기',
    },
    {
        id: 'password-input',
        icon: 'eye-off',
        label: '비밀번호',
        type: 'password',
        onIconClick: iconButtonHandler.togglePasswordVisibility,
        iconAriaLabel: '패스워드 보이기/숨기기',
    },
] as const;

const InputWrapperStyles = css`
    display: flex;
    flex-direction: column;
    gap: 1.6rem;
    width: 100%;
    margin: 5.5rem 0 1.5rem 0;
`;
