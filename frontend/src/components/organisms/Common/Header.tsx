import { css } from '@emotion/react';
import LogoWithNav from '../../molecules/Header/LogoWithNav';
import IconNavList from '../../molecules/Header/IconNavList';

export default function Header() {
    return (
        <header css={headerStyles}>
            <nav css={navStyles}>
                <LogoWithNav />
                <IconNavList />
            </nav>
        </header>
    );
}

const headerStyles = css`
    display: flex;
    align-items: flex-end;
    width: 100%;
    height: 5rem;
    padding: 0 4rem;
    box-sizing: border-box;
`;

const navStyles = css`
    width: 100%;
    display: flex;
    justify-content: space-between;
`;
