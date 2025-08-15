import { css } from '@emotion/react';
import FormButton from '../../atoms/Button/FormButton.tsx';
import TextInputWithIcon from '../../molecules/Input/TextInputWithIcon.tsx';
import RegisterCheckBoxes from '../Register/RegisterCheckBoxes.tsx';
import { iconButtonHandler, validHandler } from './utils.ts';

interface ValidationRule {
    check: (value: string) => boolean;
    message: string;
}

const inputFields = [
    {
        id: 'email-input',
        icon: 'x-circle',
        label: '이메일 주소',
        type: 'text',
        iconAriaLabel: '이메일 지우기',
        onIconClick: iconButtonHandler.clearTextByRef,
        validations: [
            {
                check: validHandler.isValidIdLength,
                message: '아이디는 6자 이상, 20자 이하이어야 합니다.',
            },
            {
                check: validHandler.isAlphaNumeric,
                message: '아이디는 영어와 숫자만 사용할 수 있습니다.',
            },
        ] as ValidationRule[],
    },
    {
        id: 'password-input',
        icon: 'eye-off',
        label: '비밀번호',
        type: 'password',
        iconAriaLabel: '비밀번호 보기',
        onIconClick: iconButtonHandler.togglePasswordVisibility,
        validations: [
            {
                check: (value) =>
                    validHandler.isPasswordMinLength(value) &&
                    validHandler.hasNumberAndLetter(value),
                message:
                    '영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.',
            },
        ] as ValidationRule[],
    },
    {
        id: 'password-confirm-input',
        icon: 'eye-off',
        label: '비밀번호 확인',
        type: 'password',
        iconAriaLabel: '비밀번호 보기',
        onIconClick: iconButtonHandler.togglePasswordVisibility,
    },
    {
        id: 'nickname-input',
        icon: 'x-circle',
        label: '닉네임',
        type: 'text',
        iconAriaLabel: '닉네임 지우기',
        onIconClick: iconButtonHandler.clearTextByRef,
    },
] as const;

export default function RegisterForm() {
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
