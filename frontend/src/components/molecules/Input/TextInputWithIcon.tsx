import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import FormLabelText from '../../atoms/Text/FormLabelText.tsx';
import TextInput from '../../atoms/Input/TextInput.tsx';
import IconButton from '../../atoms/Button/IconButton.tsx';
import WarningText from '../../atoms/Text/WarningText.tsx';

const { colors } = theme;

interface Props {
    id: string;
    label: string;
    placeholder?: string;
    onIconClick?: () => void;
    type?: InputType;
    iconAriaLabel: string;
    icon: string;
    isValid?: boolean;
    validationMessage?: string;
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
    isValid = false,
    validationMessage,
}: Props) {
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
                    />
                    <IconButton
                        iconName={icon}
                        ariaLabel={iconAriaLabel}
                        onClick={onIconClick}
                    />
                </div>
            </label>
            {!isValid && <WarningText>{validationMessage}</WarningText>}
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
