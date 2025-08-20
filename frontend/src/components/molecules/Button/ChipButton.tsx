import TextWithIcon from '../Text/TextWithIcon.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    hidden?: boolean;
    text: string;
    iconName: string;
    onClick?: (event: React.MouseEvent<HTMLElement>) => void;
}

export default function ChipButton({
    hidden = false,
    text,
    iconName,
    onClick,
}: PropsState) {
    return (
        <button onClick={onClick} css={hidden ? chipCloneStyle : chipStyle}>
            <TextWithIcon text={text} iconName={iconName} color='grey-80' />
        </button>
    );
}

const { colors } = theme;

const chipStyle = css`
    white-space: nowrap;
    width: max-content;
    height: max-content;
    text-align: center;
    padding: 0.5rem 1.25rem;
    box-sizing: border-box;
    background-color: ${colors.grey[30]};
    border: none;
    border-radius: 62.4375rem;
    cursor: pointer;
`;

const chipCloneStyle = css`
    ${chipStyle};
    visibility: hidden;
    pointer-events: none;
`;
