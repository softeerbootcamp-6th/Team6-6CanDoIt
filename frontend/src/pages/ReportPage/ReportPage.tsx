import ReportCardSection from '../../components/templates/Report/ReportCardSection.tsx';
import ReportSearchSection from '../../components/templates/Report/ReportSearchSection.tsx';
import { css } from '@emotion/react';

export default function ReportPage() {
    return (
        <div css={pageContainerStyle}>
            <div className='search-section' css={searchSectionStyle}>
                <ReportSearchSection />
            </div>
            <div className='card-wrap' css={cardSectionStyle}>
                <ReportCardSection />
            </div>
        </div>
    );
}

const pageContainerStyle = css`
    position: relative;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    max-width: 100dvw;
    overflow-x: hidden;

    height: calc(100dvh - 5rem);

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

    .search-section {
        position: relative;
        z-index: 20;
        transform: translateY(0);
        transition: transform 300ms ease;
    }

    &:not(:has(.card-wrap > *)) .search-section {
        transform: translateY(3rem);
    }

    .card-wrap > * {
        position: relative;
        opacity: 0;
        transform: translateY(3rem);
        z-index: 10;
    }

    &:has(.card-wrap > *) .card-wrap > * {
        animation: cardRise 300ms ease forwards;
    }

    @media (prefers-reduced-motion: reduce) {
        &:has(.card-wrap > *) .card-wrap > * {
            animation: none;
            opacity: 1;
            transform: none;
        }
    }
`;

const searchSectionStyle = css`
    width: 100dvw;
    margin: 1rem 0 0 0;
    height: max-content;
`;

const cardSectionStyle = css`
    overflow-x: auto;
`;
