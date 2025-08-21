import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { useState } from 'react';

interface PropsState {
    placeholder?: string;
    maxLength?: number;
}

export default function ModalTextInputBox(props: PropsState) {
    const { placeholder = '', maxLength = 100 } = props;
    const [text, setText] = useState('');

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        if (maxLength && e.target.value.length <= maxLength) {
            setText(e.target.value);
        }
    };

    return (
        <div css={textInputBoxContainerStyle}>
            <textarea
                css={textareaStyle}
                name='content'
                required
                value={text}
                onChange={handleChange}
                maxLength={maxLength}
                placeholder={placeholder}
            />
            <div css={counterStyle}>
                {text.length}/{maxLength}
            </div>
        </div>
    );
}

const { colors, typography } = theme;

const textInputBoxContainerStyle = css`
    width: 36.0625rem;
    height: 10rem;
    padding: 1.25rem;
    box-sizing: border-box;
    border-radius: 1.25rem;
    background-color: ${colors.grey[50]};
    position: relative;
`;

const textareaStyle = css`
    width: 100%;
    height: 100%;
    border: none;
    background: transparent;
    color: ${colors.grey[0]};
    resize: none;
    outline: none;

    font-size: ${typography.fontSize.body};
    font-weight: ${typography.fontWeight.medium};
    line-height: 150%;

    &::placeholder {
        color: ${colors.grey[70]};
    }
`;

const counterStyle = css`
    position: absolute;
    bottom: 1.25rem;
    right: 1.25rem;
    color: ${colors.grey[70]};

    font-size: ${typography.fontSize.body};
    font-weight: ${typography.fontWeight.medium};
    line-height: 150%;
`;
