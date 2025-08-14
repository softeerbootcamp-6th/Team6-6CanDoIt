import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { forwardRef } from 'react';

const { colors, typography } = theme;

interface TextInputProps {
    id?: string;
    ariaLabel?: string;
    placeholder?: string;
    type: InputType;
}

type InputType = 'text' | 'password';

const TextInput = forwardRef<HTMLInputElement, TextInputProps>(
    ({ id, ariaLabel, placeholder, type }, ref) => {
        return (
            <input
                ref={ref}
                id={id}
                aria-label={ariaLabel}
                placeholder={placeholder}
                type={type}
                css={inputStyles}
            />
        );
    },
);

export default TextInput;

const inputStyles = css`
    all: unset;
    box-sizing: border-box;
    min-width: 26rem;
    height: 1.5rem;
    color: ${colors.grey[90]};
    font-weight: ${typography.fontWeight.medium};
    cursor: text;
    font-size: ${typography.fontSize.caption};

    &::placeholder {
        color: ${colors.grey[60]};
    }

    &:focus {
        outline: none;
        border-bottom: 2px solid ${colors.primary};
    }
`;
