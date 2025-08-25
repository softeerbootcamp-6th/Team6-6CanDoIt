import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    onClick?: () => void;
    isSelected?: boolean;
    children: React.ReactNode;
}

export default function FilterLabelButton(props: PropsState) {
    const { children, onClick, isSelected = false } = props;
    return (
        <button
            type='button'
            onClick={onClick}
            css={filterLabelTextStyle({ isSelected })}
        >
            {children}
        </button>
    );
}

const { colors, typography } = theme;

const filterLabelTextStyle = ({
    isSelected = false,
}: {
    isSelected?: boolean;
}) => css`
    width: max-content;
    padding: 0.25rem 0.75rem;
    box-sizing: border-box;
    border-radius: 0.5rem;
    background-color: ${isSelected ? colors.primary.normal : colors.grey[40]};
    white-space: nowrap;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.bold};
    color: ${isSelected ? colors.grey[0] : colors.grey[100]};
    border: none;
    cursor: pointer;
`;
