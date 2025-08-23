import { css } from '@emotion/react';
import { useLocation } from 'react-router-dom';
import LogoWithNav from '../../molecules/Header/LogoWithNav';
import IconNavList from '../../molecules/Header/IconNavList';
import { LabelHeading } from '../../atoms/Heading/Heading';
import Icon from '../../atoms/Icon/Icons';

export default function Header() {
    const location = useLocation();
    const isForecastPage = location.pathname.includes('/forecast');

    return (
        <header css={headerStyles}>
            <nav css={navStyles}>
                {isForecastPage ? (
                    <>
                        <Icon
                            name='back'
                            width={3}
                            height={3}
                            color='grey-100'
                            storkeWidth={3}
                        />
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

const headerStyles = css`
    position: sticky;
    top: 0;
    z-index: 10000;
    display: flex;
    align-items: flex-end;
    width: 100%;
    height: 5rem;
    padding: 0 4rem;
    box-sizing: border-box;
    background: linear-gradient(
        to bottom,
        rgba(0, 0, 0, 0.5) 0%,
        rgba(0, 0, 0, 0) 100%
    );
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
