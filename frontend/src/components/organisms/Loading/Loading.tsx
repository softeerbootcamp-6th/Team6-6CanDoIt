import { DisplayHeading } from '../../atoms/Heading/Heading.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import { css, keyframes } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    mountainTitle: string;
    mountainDescription: string;
}

export default function Loading(props: PropsState) {
    const { mountainTitle, mountainDescription } = props;

    return (
        <div css={fullScreenContainerStyle}>
            <div css={loadingPageBackGroundStyle} />
            <div css={loadingProgressStyle} />
            <div css={overBackgroundStyle}>
                <DisplayHeading HeadingTag='h1'>{mountainTitle}</DisplayHeading>
                <CommonText TextTag='p' fontSize='body' fontWeight='medium'>
                    {mountainDescription}
                </CommonText>
            </div>
        </div>
    );
}

const { colors } = theme;

const fullScreenContainerStyle = css`
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: ${colors.grey[0]};
`;

const fillProgress = keyframes`
  from { transform: scaleX(0); }
  to   { transform: scaleX(1); }
`;

const loadingProgressStyle = css`
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 0.2rem;
    z-index: 10;
    background-color: ${colors.grey[100]};

    transform-origin: left center;
    transform: scaleX(0);
    will-change: transform;

    animation: ${fillProgress} 3s linear forwards;
`;

const overBackgroundStyle = css`
    position: relative;
    z-index: 10;

    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 60%;
    height: 100vh;
    gap: 2rem;
    margin: 0 auto;
`;

const loadingPageBackGroundStyle = css`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0.15;
    filter: blur(50px);
`;
