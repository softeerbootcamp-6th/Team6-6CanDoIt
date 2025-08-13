import { css } from '@emotion/react';
import FormButton from '../../atoms/Button/FormButton.tsx';
import TextInputWithIcon from '../../molecules/Input/TextInputWithIcon.tsx';
import RegisterCheckBoxes from '../Register/RegisterCheckBoxes.tsx';

export default function RegisterForm() {
    const inputFields = [
        {
            id: 'email-input',
            icon: 'x-circle',
            label: '이메일 주소',
            type: 'text',
            iconAriaLabel: '이메일 지우기',
            validationMessage: '이메일 형식이 올바르지 않습니다.',
        },
        {
            id: 'password-input',
            icon: 'eye-off',
            label: '비밀번호',
            type: 'password',
            iconAriaLabel: '비밀번호 보기',
            validationMessage: '비밀번호 형식이 올바르지 않습니다.',
        },
        {
            id: 'password-confirm-input',
            icon: 'eye-off',
            label: '비밀번호 확인',
            type: 'password',
            iconAriaLabel: '비밀번호 보기',
            validationMessage: '비밀번호가 일치하지 않습니다.',
        },
        {
            id: 'nickname-input',
            icon: 'x-circle',
            label: '닉네임',
            type: 'text',
            iconAriaLabel: '닉네임 지우기',
            validationMessage: '닉네임을 입력해주세요.',
        },
    ] as const;

    return (
        <form css={formWrapperStyles}>
            <TextInputWithIcon {...inputFields[0]} />
            <FormButton text='로그인' />

            {inputFields.slice(1).map((field) => (
                <TextInputWithIcon key={field.id} {...field} />
            ))}
            <RegisterCheckBoxes />
            <FormButton text='회원가입하기' />
        </form>
    );
}

const formWrapperStyles = css`
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
`;
