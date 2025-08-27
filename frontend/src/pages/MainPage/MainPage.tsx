import { useState } from 'react';
import { css } from '@emotion/react';
import MainSearchSection from '../../components/templates/Main/MainSearchSection.tsx';
import MountainCardSection from '../../components/templates/Main/MountainCardSection.tsx';
import bg from '../../assets/bg.png';

export default function MainPage() {
    const [isOpen, setIsOpen] = useState(false);

    const formValidChangeHandler = (isValid: boolean) => setIsOpen(!isValid);
    const backgroundClickHandler = () => setIsOpen(false);

    return (
        <div css={pageWrapperStyle}>
            <img src={bg} alt='' css={backgroundImgStyle} />
            {isOpen && (
                <div css={backgroundStyle} onClick={backgroundClickHandler} />
            )}
            <div css={[overBackgroundStyle, isOpen && centeredStyle]}>
                <MainSearchSection onFormValidChange={formValidChangeHandler} />
            </div>
            <MountainCardSection />
        </div>
    );
}

const backgroundImgStyle = css`
    position: fixed;
    inset: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    z-index: 0;
    pointer-events: none;
    user-select: none;
`;

const pageWrapperStyle = css`
    width: 100%;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    position: relative;
`;

const backgroundStyle = css`
    position: fixed;
    inset: 0;
    opacity: 0.7;
    background: linear-gradient(180deg, #000 0%, rgba(0, 0, 0, 0) 100%);
    z-index: 10;
    transition: opacity 250ms ease;
`;

const overBackgroundStyle = css`
    position: relative;
    z-index: 20;
    width: max-content;
    margin: 0 auto;
    padding-top: 1rem;
    box-sizing: border-box;

    display: flex;
    flex-direction: column;
    gap: 2rem;

    transform: translate(0, 0);
    transform-origin: center center;
    transition:
        transform 350ms ease,
        box-shadow 250ms ease,
        filter 250ms ease;
    will-change: transform;
`;

const centeredStyle = css`
    transform: translate(0, 2rem) scale(1.1);
`;
