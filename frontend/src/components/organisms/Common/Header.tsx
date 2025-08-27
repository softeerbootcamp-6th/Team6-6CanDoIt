import { css } from '@emotion/react';
import { useLocation, useNavigate } from 'react-router-dom';
import LogoWithNav from '../../molecules/Header/LogoWithNav';
import IconNavList from '../../molecules/Header/IconNavList';
import { LabelHeading } from '../../atoms/Heading/Heading';
import Icon from '../../atoms/Icon/Icons';
import { theme } from '../../../theme/theme';

export default function Header() {
    const location = useLocation();
    const isForecastPage = location.pathname.includes('/forecast');
    const isBackgroundPage = ['/alert', '/login'].some((path) =>
        location.pathname.includes(path),
    );
    const navigate = useNavigate();

    return (
        <header css={headerStyles(isForecastPage, !isBackgroundPage)}>
            <nav css={navStyles}>
                {isForecastPage ? (
                    <>
                        <button
                            css={css`
                                all: unset;
                                cursor: pointer;
                            `}
                            onClick={() => navigate('/')}
                        >
                            <Icon
                                name='back'
                                width={3}
                                height={3}
                                color='grey-100'
                                storkeWidth={3}
                            />
                        </button>
                        <LabelHeading HeadingTag='h1'>일기 예보</LabelHeading>
                        <IconNavList />
                    </>
                ) : (
                    <>
                        <LogoWithNav />
                        <IconNavList />
                    </>
                )}
            </nav>
        </header>
    );
}

const { colors } = theme;

const headerStyles = (isForecastPage: boolean, isTransparent: boolean) => css`
    position: sticky;
    top: 0;
    z-index: 10000;
    display: flex;
    align-items: flex-end;
    width: 100%;
    height: 5rem;
    padding: 0 4rem;
    box-sizing: border-box;

    background: ${isForecastPage
        ? `linear-gradient(
                to bottom,
                rgba(0, 0, 0, 0.5) 0%,
                rgba(0, 0, 0, 0) 100%
            )`
        : isTransparent
          ? 'transparent'
          : colors.grey[0]};
`;

const navStyles = css`
    width: 100%;
    display: flex;
    justify-content: space-between;

    & h1 {
        display: flex;
        align-items: center;
        justify-content: center;
    }
`;
