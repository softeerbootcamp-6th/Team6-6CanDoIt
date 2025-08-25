import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import DigitInput from '../../atoms/Input/DigitInput.tsx';

interface PropsState {
    day: string;
}

export default function TimeInput({ day }: PropsState) {
    return (
        <div css={wrapperStyle}>
            <CommonText {...dayTextProps}>{day}</CommonText>

            <div css={inputContainerStyle}>
                <DigitInput placeholder='09' />
                <CommonText {...separatorTextProps}>:</CommonText>
                <DigitInput placeholder='00' />
            </div>
        </div>
    );
}

const dayTextProps = {
    TextTag: 'span',
    fontSize: 'body',
    fontWeight: 'medium',
    color: 'grey-70',
} as const;

const separatorTextProps = {
    TextTag: 'span',
    fontSize: 'label',
    fontWeight: 'medium',
    lineHeight: 1.5,
    color: 'grey-10',
} as const;

const { colors } = theme;

const wrapperStyle = css`
    width: max-content;
    display: flex;
    align-items: center;
    gap: 1.25rem;
`;

const inputContainerStyle = css`
    background-color: ${colors.grey[100]};
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 0.625rem;
`;
