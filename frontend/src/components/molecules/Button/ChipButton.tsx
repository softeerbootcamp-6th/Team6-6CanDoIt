import TextWithIcon from '../Text/TextWithIcon.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    text: string;
    iconName: string;
}

export default function ChipButton({ text, iconName }: propsState) {
    return (
        <button css={chipStyle}>
            <TextWithIcon text={text} iconName={iconName} color='grey-80' />
        </button>
    );
}

const { colors } = theme;

const chipStyle = css`
    width: max-content;
    height: max-content;
    text-align: center;
    padding: 0.5rem 1.25rem;
    background-color: ${colors.grey[30]};
    border-radius: 62.4375rem;
    cursor: pointer;
`;
