import { css } from '@emotion/react';
import NavItemLink from '../../atoms/Link/NavItemLink';

const navItemList = [
    { to: '/', label: '국립공원 날씨 예보' },
    { to: '/report', label: '실시간 제보' },
    { to: '/alert', label: '안전 정보' },
];

export default function NavItemList() {
    return (
        <ul css={ulStyles}>
            {navItemList.map((item) => (
                <li key={item.to}>
                    <NavItemLink to={item.to}>{item.label}</NavItemLink>
                </li>
            ))}
        </ul>
    );
}

const ulStyles = css`
    display: flex;
    gap: 2rem;
`;
