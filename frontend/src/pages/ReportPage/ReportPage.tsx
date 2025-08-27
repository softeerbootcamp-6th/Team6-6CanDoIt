import ReportCardSection from '../../components/templates/Report/ReportCardSection.tsx';
import ReportSearchSection from '../../components/templates/Report/ReportSearchSection.tsx';
import { css } from '@emotion/react';
import bg from '../../assets/bg.png';

export default function ReportPage() {
    return (
        <div css={outerContainerStyle}>
            <img src={bg} alt='' css={backgroundImgStyle} />
            <div css={pageContainerStyle}>
                <div css={searchSectionStyle}>
                    <ReportSearchSection />
                </div>
                <div css={cardSectionStyle}>
                    <ReportCardSection />
                </div>
            </div>
        </div>
    );
}
const outerContainerStyle = css`
    position: relative;
    width: 100vw;
    height: calc(100dvh - 5rem);
    overflow: hidden;
`;

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

const pageContainerStyle = css`
    position: relative;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    max-width: 100dvw;
    overflow-x: hidden;
    padding-top: 1rem;
    box-sizing: border-box;
    height: 100%;
    z-index: 1;

    @keyframes cardRise {
        from {
            transform: translateY(2rem);
            opacity: 0;
        }
        to {
            transform: translateY(0);
            opacity: 1;
        }
    }

    &:not(:has(> :nth-child(2) > *)) > :first-child {
        transform: translateY(11rem);
    }

    > :nth-child(2) > * {
        position: relative;
        opacity: 0;
        transform: translateY(4rem);
    }

    &:has(> :nth-child(2) > *) > :nth-child(2) > * {
        animation: cardRise 300ms forwards;
    }
`;

const searchSectionStyle = css`
    width: 100dvw;
    margin: 0;
    height: max-content;
    transform: translateY(0);
    transition: transform 200ms;
    z-index: 10;
`;

const cardSectionStyle = css`
    overflow-x: auto;
    overflow-y: hidden;
`;
