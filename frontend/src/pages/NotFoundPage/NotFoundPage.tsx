import { css } from '@emotion/react';

import backgroundImg from '../../assets/bg.png';

import { HeadlineHeading } from '../../components/atoms/Heading/Heading';
import { useNavigate } from 'react-router-dom';
import { theme } from '../../theme/theme';

export default function NotFoundPage() {
    const navigate = useNavigate();

    return (
        <div css={wrppaerStyles}>
            <HeadlineHeading HeadingTag='h1'>
                지금 들어오신 페이지는 없는 페이지 입니다.
            </HeadlineHeading>
            <button css={buttonStyles} onClick={() => navigate('/')}>
                홈으로
            </button>
        </div>
    );
}

const { colors, typography } = theme;

const wrppaerStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: calc(100dvh - 5rem);
    gap: 4rem;

    background: url(${backgroundImg}) no-repeat center center;
    background-size: cover;
    padding-bottom: 5rem;
    box-sizing: border-box;
`;

const buttonStyles = css`
    all: unset;
    color: ${colors.grey[100]};
    font-size: ${typography.fontSize.title};
    font-weight: ${typography.fontWeight.bold};
    width: 10rem;
    height: 4rem;
    border-radius: 1rem;
    diplay: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    background-color: ${colors.status.normal.excellent};

    &:hover {
        opacity: 0.8;
        cursor: pointer;
    }
`;
