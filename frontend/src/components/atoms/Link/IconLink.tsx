import { NavLink } from 'react-router-dom';
import Icon from '../Icon/Icons';
import { css } from '@emotion/react';

interface PropsState {
    to: string;
    name: string;
    size?: number;
    color?: string;
}

export default function IconLink({
    to,
    name,
    size = 1.9,
    color = 'grey-100',
}: PropsState) {
    return (
        <NavLink
            css={css`
                display: flex;
                align-items: center;
            `}
            to={to}
        >
            <Icon name={name} width={size} height={size} color={color} />
        </NavLink>
    );
}
