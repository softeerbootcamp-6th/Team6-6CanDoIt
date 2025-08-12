import { css } from '@emotion/react';
import RegisterHeader from '../../molecules/Register/RegisterHeader';
import RegisterForm from '../../organisms/Form/RegisterForm';

export default function RegisterTemplate() {
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
