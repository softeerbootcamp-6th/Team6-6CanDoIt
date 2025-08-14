import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import FormLabelText from '../../atoms/Text/FormLabelText.tsx';
import TextInput from '../../atoms/Input/TextInput.tsx';
import IconButton from '../../atoms/Button/IconButton.tsx';
import WarningText from '../../atoms/Text/WarningText.tsx';
import { useRef, useState } from 'react';

const { colors } = theme;

interface PropsState {
    id: string;
    label: string;
    placeholder?: string;
    onIconClick?: (ref: React.RefObject<HTMLInputElement>) => void;
    type?: InputType;
    iconAriaLabel: string;
    icon: string;
    validations?: ValidationRule[];
}

interface ValidationRule {
    check: (value: string) => boolean;
    message: string;
}

type InputType = 'text' | 'password';

export default function TextInputWithIcon({
    id,
    label,
    placeholder,
    onIconClick,
    type = 'text',
    icon,
    iconAriaLabel,
    validations = [],
}: PropsState) {
    const inputRef = useRef<null | HTMLInputElement>(null);
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleChange = (text: string) => {
        const newValue = text;

        for (const rule of validations) {
            if (!rule.check(newValue)) {
                setErrorMessage(rule.message);
                return;
            }
        }
        setErrorMessage('');
    };

    return (
        <div>
            <label css={wrapperStyles} htmlFor={id}>
                <FormLabelText text={label} />
                <div css={lineStyles}>
                    <TextInput
                        id={id}
                        ariaLabel={label}
                        placeholder={placeholder}
                        type={type}
                        ref={inputRef}
                        onChange={(text) => handleChange(text)}
                    />
                    <IconButton
                        iconName={icon}
                        ariaLabel={iconAriaLabel}
                        onClick={() => onIconClick?.(inputRef)}
                    />
                </div>
            </label>
            {errorMessage && <WarningText>{errorMessage}</WarningText>}
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    border-bottom: 1px solid ${colors.grey[60]};
    width: fit-content;
    gap: 0.5rem;
`;

const lineStyles = css`
    display: flex;
    align-items: center;
    padding: 0.75rem 0.2rem;
    gap: 0.5rem;
`;
