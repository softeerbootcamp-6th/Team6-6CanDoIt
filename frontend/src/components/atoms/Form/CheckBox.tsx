import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import type { ColorValueType } from '../../../types/themeTypes';

interface PropsState {
    id: string;
    text: string;
    subTitle?: string;
    grey?: ColorValueType;
}

const { colors, typography } = theme;

export default function CheckBox({
    id,
    text,
    subTitle,
    grey = 90 as ColorValueType,
}: PropsState) {
    const dynamicColorStyles = css`
        color: ${colors.grey[grey]};
    `;

    return (
        <label htmlFor={id} css={wrapperStyles}>
            <input id={id} type='checkbox' css={hiddenInput} />
            <span css={boxStyles} />
            <div css={labelBoxStyles}>
                <span css={[labelStyles, dynamicColorStyles]}>{text}</span>
                {subTitle && <span css={subTitleStyles}>{subTitle}</span>}
            </div>
        </label>
    );
}

const wrapperStyles = css`
    display: flex;
    align-items: center;
    gap: 0.5rem;
    cursor: pointer;
`;

const boxStyles = css`
    position: relative;
    width: 1.1rem;
    height: 1.1rem;
    border: 3px solid ${colors.grey[100]};
    border-radius: 6px;
    transition: all 0.15s ease-in-out;
`;

const hiddenInput = css`
    position: absolute;
    opacity: 0;
    pointer-events: none;

    &:checked + span {
        background-color: ${colors.grey[60]};
        border-color: ${colors.grey[60]};
    }

    &:checked + span::after {
        content: '';
        position: absolute;
        width: 0.4rem;
        height: 0.65rem;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -70%) rotate(45deg);
        border-right: 2px solid ${colors.grey[100]};
        border-bottom: 2px solid ${colors.grey[100]};
    }
`;

const labelBoxStyles = css`
    display: flex;
    flex-direction: column;
    gap: 0.2rem;
`;

const labelStyles = css`
    font-size: ${typography.fontSize.caption};
    color: ${colors.grey[90]};
`;

const subTitleStyles = css`
    color: ${colors.grey[70]};
    font-weight: ${typography.fontWeight.regular};
    font-size: 0.75rem;
`;
