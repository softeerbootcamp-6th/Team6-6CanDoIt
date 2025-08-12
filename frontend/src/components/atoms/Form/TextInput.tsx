import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

const { colors, typography } = theme;

interface TextInputProps {
    id?: string;
    ariaLabel?: string;
    placeholder?: string;
    type: InputType;
}

type InputType = 'text' | 'password';

export default function TextInput({
    id,
    ariaLabel,
    placeholder,
    type,
}: TextInputProps) {
    return (
        <input
            id={id}
            aria-label={ariaLabel}
            placeholder={placeholder}
            css={inputStyles}
            type={type}
        />
    );
}

const inputStyles = css`
    all: unset;
    box-sizing: border-box;
    min-width: 26rem;
    height: 1.5rem;
    color: ${colors.grey[90]};
    font-weight: ${typography.fontWeight.medium};
    cursor: text;
    font-size: ${typography.fontSize.caption}
    line-height: 1.4rem;

    &::placeholder {
        color: ${colors.grey[60]};
    }

    &:focus {
        outline: none;
        border-bottom: 2px solid ${colors.primary};
    }
`;
