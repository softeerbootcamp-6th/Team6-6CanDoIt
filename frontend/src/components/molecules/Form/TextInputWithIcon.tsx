import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import LabelText from '../../atoms/Form/LabelText';
import TextInput from '../../atoms/Form/TextInput';
import IconButton from '../../atoms/Form/IconButton';
import WarningText from '../../atoms/Form/WarningText';

const { colors } = theme;

interface Props {
    id: string;
    label: string;
    placeholder?: string;
    onIconClick?: () => void;
    type?: InputType;
    iconLabel: string;
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
    iconLabel,
    isValid = false,
    validationMessage,
}: Props) {
    return (
        <div>
            <label css={wrapperStyles} htmlFor={id}>
                <LabelText text={label} />
                <div css={lineStyles}>
                    <TextInput
                        id={id}
                        ariaLabel={label}
                        placeholder={placeholder}
                        type={type}
                    />
                    <IconButton
                        iconName={icon}
                        ariaLabel={iconLabel}
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
