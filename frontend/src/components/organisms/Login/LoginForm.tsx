import { css } from '@emotion/react';
import FormButton from '../../atoms/Button/FormButton.tsx';
import CheckBox from '../../atoms/Form/CheckBox.tsx';
import TextInputWithIcon from '../../molecules/Input/TextInputWithIcon.tsx';
import type { ColorValueType } from '../../../types/themeTypes';

export default function LoginForm() {
    return (
        <form>
            <div css={InputWrapperStyles}>
                {inputFields.map((field) => (
                    <TextInputWithIcon
                        key={field.id}
                        id={field.id}
                        icon={field.icon}
                        label={field.label}
                        type={field.type}
                        iconAriaLabel={field.iconAriaLabel}
                    />
                ))}
            </div>
            <CheckBox
                id='login-button'
                text='자동 로그인'
                grey={60 as ColorValueType}
            />
            <FormButton text='로그인' margin='2.5rem 0 0 0' />
        </form>
    );
}

const inputFields = [
    {
        id: 'email-input',
        icon: 'x-circle',
        label: '이메일 주소',
        type: 'text',
        iconAriaLabel: '이메일 지우기',
    },
    {
        id: 'password-input',
        icon: 'eye-off',
        label: '비밀번호',
        type: 'password',
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
