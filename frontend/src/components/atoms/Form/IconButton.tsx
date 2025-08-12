import { css } from '@emotion/react';
import Icon from '../Icon/Icons';

interface IconButtonProps {
    iconName: string;
    ariaLabel: string;
    onClick?: () => void;
}

export default function IconButton({
    iconName,
    ariaLabel,
    onClick,
}: IconButtonProps) {
    return (
        <button
            css={iconButtonStyles}
            type='button'
            aria-label={ariaLabel}
            onClick={onClick}
        >
            <Icon name={iconName} width={1.5} height={1.5} color='grey-60' />
        </button>
    );
}

const iconButtonStyles = css`
    all: unset;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
`;
