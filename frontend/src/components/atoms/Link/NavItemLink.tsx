import { NavLink } from 'react-router-dom';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    to: string;
    children: React.ReactNode;
}

export default function NavItemLink({ to, children }: PropsState) {
    return (
        <NavLink to={to} css={navLinkStyles}>
            {children}
        </NavLink>
    );
}

const { colors, typography } = theme;

const navLinkStyles = css`
    all: unset;
    position: relative;
    cursor: pointer;
    padding-bottom: 0.3rem;
    color: ${colors.greyOpacityWhite[40]};
    font-size: ${typography.fontSize.label};
    font-weight: ${typography.fontWeight.medium};
    line-height: 150%;

    &::before {
        content: '';
        position: absolute;
        width: 100%;
        height: 2px;
        bottom: 0;
        left: 0;
        background-color: ${colors.grey[100]};
        transform: scaleX(0);
        transform-origin: left center;
        transition: transform 0.3s ease;
    }

    &:hover::before {
        transform: scaleX(1);
    }

    &:hover {
        color: ${colors.grey[100]};
    }

    &.active {
        color: ${colors.grey[100]};
    }

    &.active::before {
        color: ${colors.grey[100]};
        transform: scaleX(1);
    }
`;
