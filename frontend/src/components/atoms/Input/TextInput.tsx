import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { forwardRef } from 'react';

const { colors, typography } = theme;

interface PropsState {
    id?: string;
    ariaLabel?: string;
    placeholder?: string;
    type: InputType;
    onInput?: (value: string) => void;
}

type InputType = 'text' | 'password';

const TextInput = forwardRef<HTMLInputElement, PropsState>(
    ({ id, ariaLabel, placeholder, type, onInput }, ref) => {
        const handleInput = (e: React.FormEvent<HTMLInputElement>) => {
            onInput?.((e.target as HTMLInputElement).value);
        };

        return (
            <input
                ref={ref}
                id={id}
                aria-label={ariaLabel}
                placeholder={placeholder}
                type={type}
                onInput={handleInput}
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
