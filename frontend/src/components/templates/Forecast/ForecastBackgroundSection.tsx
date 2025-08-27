import { useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { useSearchParams } from 'react-router-dom';

export default function ForecastBackgroundSection() {
    const [searchParams] = useSearchParams();
    const courseId = Number(searchParams.get('courseid'));
    const backgroundImageIndex = (courseId % 3) + 1;

    const layer1Ref = useRef<HTMLDivElement>(null);
    const layer2Ref = useRef<HTMLDivElement>(null);
    const layer3Ref = useRef<HTMLDivElement>(null);

    useEffect(() => {
        let ticking = false;

        const handleScroll = () => {
            const scrollY = window.scrollY;
            const pageHeight = window.innerHeight;
            const limit = pageHeight * 0.05;
            const effectiveScroll = Math.min(scrollY, limit);

            if (!ticking) {
                ticking = true;
                window.requestAnimationFrame(() => {
                    if (layer2Ref.current) {
                        layer2Ref.current.style.transform = `translate3d(0, ${-(effectiveScroll * 0.4)}px, 0)`;
                    }
                    if (layer3Ref.current) {
                        layer3Ref.current.style.transform = `translate3d(0, ${-(effectiveScroll * 0.8)}px, 0)`;
                    }
                    ticking = false;
                });
            }
        };

        window.addEventListener('scroll', handleScroll, { passive: true });
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    return (
        <div>
            <div ref={layer1Ref} css={topLayerStyles(backgroundImageIndex)} />
            <div
                ref={layer2Ref}
                css={middleLayerStyles(backgroundImageIndex)}
            />
            <div
                ref={layer3Ref}
                css={bottomLayerStyles(backgroundImageIndex)}
            />
            <div css={overlayGradient} />
        </div>
    );
}

const topLayerStyles = (index: number) => css`
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 100%;
    background-image: url('/images/mountain${index}_layer3.webp');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    z-index: -40;
    will-change: transform;
`;

const middleLayerStyles = (index: number) => css`
    position: absolute;
    top: 5%;
    left: 0;
    right: 0;
    height: 110%;
    background-image: url('/images/mountain${index}_layer2.webp');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    z-index: -30;
    will-change: transform;
`;

const bottomLayerStyles = (index: number) => css`
    position: absolute;
    top: 15%;
    left: 0;
    right: 0;
    height: 120%;
    background-image: url('/images/mountain${index}_layer1.webp');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    z-index: -20;
    will-change: transform;
`;

const overlayGradient = css`
    position: absolute;
    inset: 0;
    background: linear-gradient(
        to bottom,
        rgba(0, 0, 0, 0.2) 0%,
        rgba(0, 0, 0, 0.5) 50%,
        rgba(0, 0, 0, 0.8) 80%,
        rgba(0, 0, 0, 1) 100%
    );
    z-index: -11;
    pointer-events: none;
`;
