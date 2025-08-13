import { css } from '@emotion/react';
import { NavLink } from 'react-router-dom';
import NavItemList from './NavItemList';
import LogoImage from '../../atoms/Image/LogoImage';

export default function LogoWithNav() {
    return (
        <div css={wrapperStyles}>
            <NavLink to='/'>
                <LogoImage />
            </NavLink>
            <NavItemList />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    gap: 3rem;
`;
