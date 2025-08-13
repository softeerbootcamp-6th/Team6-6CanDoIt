import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    text: string;
    onClick?: () => void;
    margin?: string;
}

const { colors, typography } = theme;

export default function FormButton({ text, onClick, margin }: PropsState) {
    const dynamicStyles = css`
        margin: ${margin};
    `;

    return (
        <button
            css={[buttonStyles, dynamicStyles]}
            onClick={onClick}
            type='submit'
        >
            {text}
        </button>
    );
}

const buttonStyles = css`
    display: flex;
    color: ${colors.grey[20]};
    background: ${colors.grey[98]};
    width: 28rem;
    height: 3.5rem;
    padding: 0.5rem 1.25rem;

    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.bold};
    line-height: 150%;
    border-radius: 0.75rem;

    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    flex-shrink: 0;
    cursor: pointer;
`;
