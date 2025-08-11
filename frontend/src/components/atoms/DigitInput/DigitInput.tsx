import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

export default function DigitInput({ placeholder }: { placeholder: string }) {
    return (
        <input
            css={digitInputStyle}
            type='text'
            maxLength={2}
            placeholder={placeholder}
        />
    );
}

const { colors, typography } = theme;

const digitInputStyle = css`
    width: 3.59375rem;
    height: 2.5rem;
    border: none;
    background: transparent;
    text-align: center;
    font-size: ${typography.fontSize.label};
    font-weight: ${typography.fontWeight.medium};
    line-height: 1.5;
    color: ${colors.grey[10]};
    outline: none;
`;
