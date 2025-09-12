import { css } from '@emotion/react';

import type { ColorValueType } from '../../../types/themeTypes';
import { iconButtonHandler } from '../Register/utils.ts';

import Modal from '../../molecules/Modal/RegisterModal.tsx';
import PendingModal from '../../molecules/Modal/ReportPendingModal.tsx';
import TextInputWithIcon from '../../molecules/Input/TextInputWithIcon.tsx';
import FormButton from '../../atoms/Button/FormButton.tsx';
import CheckBox from '../../atoms/Form/CheckBox.tsx';

import { useLoginForm } from '../../../hooks/useLoginForm.ts';

export default function LoginForm() {
    const { refs, errorMessage, setErrorMessage, isLoading, handleSubmit } =
        useLoginForm({ redirectTo: '/' });

    const inputFieldsWithRef = [
        { ...inputFields[0], inputRef: refs.idRef },
        { ...inputFields[1], inputRef: refs.passwordRef },
    ];

    return (
        <>
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
                    inputRef={refs.autoLoginRef}
                    id='login-button'
                    text='자동 로그인'
                    grey={60 as ColorValueType}
                />
                <FormButton
                    text='로그인'
                    onClick={handleSubmit}
                    margin='2.5rem 0 0 0'
                />
            </form>

            {errorMessage && (
                <Modal onClose={() => setErrorMessage('')}>
                    {errorMessage}
                </Modal>
            )}

            {isLoading && <PendingModal />}
        </>
    );
}

const inputFields = [
    {
        id: 'email-input',
        icon: 'x-circle',
        label: '아이디',
        type: 'text',
        onIconClick: iconButtonHandler.clearTextByRef,
        iconAriaLabel: '아이디 지우기',
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
