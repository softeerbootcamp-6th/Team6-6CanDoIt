import { useEffect } from 'react';
import DetailInfoSection from '../../components/templates/Forecast/DetailInfoSection.tsx';
import ForecastBackgroundSection from '../../components/templates/Forecast/ForecastBackgroundSection.tsx';
import ForecastSearchSection from '../../components/templates/Forecast/ForecastSearchSection.tsx';
import SummaryInfoSection from '../../components/templates/Forecast/SummaryInfoSection.tsx';
import { css } from '@emotion/react';
import { theme } from '../../theme/theme.ts';

export default function ForecastPage() {
    useEffect(() => {
        let isScrolling = false;

        const handleWheel = (e: WheelEvent) => {
            if (isScrolling) return;

            const pageHeight = window.innerHeight;
            const scrollY = window.scrollY;
            const currentPage = Math.round(scrollY / pageHeight);

            const offsetInPage = scrollY % pageHeight;
            const downThreshold = pageHeight * 0.3;
            const upThreshold = pageHeight * 0.4;

            let targetPage = currentPage;

            if (e.deltaY > 0) {
                if (offsetInPage > downThreshold) {
                    targetPage = currentPage + 1;
                }
            } else {
                if (offsetInPage < upThreshold) {
                    targetPage = currentPage - 1;
                }
            }

            if (
                targetPage !== currentPage &&
                targetPage >= 0 &&
                targetPage <= 1
            ) {
                isScrolling = true;
                window.scrollTo({
                    top: targetPage * pageHeight,
                    behavior: 'smooth',
                });

                setTimeout(() => {
                    isScrolling = false;
                }, 800);
            }
        };

        window.addEventListener('wheel', handleWheel, { passive: false });
        return () => window.removeEventListener('wheel', handleWheel);
    }, []);

    return (
        <div>
            <ForecastBackgroundSection />
            <ForecastSearchSection />
            <SummaryInfoSection />
            <div css={wrapperStyles}>
                <div css={wholeWrapper}>
                    <DetailInfoSection />
                </div>
            </div>
        </div>
    );
}

const { colors } = theme;

const wrapperStyles = css`
    position: relative;
    z-index: 100;
    background-color: ${colors.grey[0]};
    width: 100%;
    padding-top: 5rem;
    box-sizing: border-box;
    height: 100dvh;
    display: flex;
    flex-direction: column;
`;

const wholeWrapper = css`
    height: 100%;
    display: flex;
    position: relative;
`;
