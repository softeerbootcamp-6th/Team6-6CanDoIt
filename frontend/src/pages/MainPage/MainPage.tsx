import { useState } from 'react';
import { css } from '@emotion/react';
import MainSearchSection from '../../components/templates/Main/MainSearchSection.tsx';
import MountainCardSection from '../../components/templates/Main/MountainCardSection.tsx';

export default function MainPage() {
    const [isOpen, setIsOpen] = useState(false);

    const formValidChangeHandler = (isValid: boolean) => setIsOpen(!isValid);
    const backgroundClickHandler = () => setIsOpen(false);

    return (
        <>
            {isOpen && (
                <div css={backgroundStyle} onClick={backgroundClickHandler} />
            )}
            <div css={overBackgroundStyle}>
                <MainSearchSection onFormValidChange={formValidChangeHandler} />
            </div>
            <MountainCardSection />
        </>
    );
}

const backgroundStyle = css`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.7;
    background: linear-gradient(180deg, #000 0%, rgba(0, 0, 0, 0) 100%);
`;

const overBackgroundStyle = css`
    position: relative;
    z-index: 10;

    display: flex;
    flex-direction: column;
    gap: 2rem;

    margin-bottom: 2rem;
`;
