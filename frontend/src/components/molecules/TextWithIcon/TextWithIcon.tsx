import { css } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';

interface PropsState {
    text: string;
    iconName: string;
    color: string;
}

export default function TextWithIcon({ text, iconName, color }: PropsState) {
    return (
        <div css={wrapperStyles}>
            <Icon name={iconName} width={1.5} height={1.5} color={color} />
            <CommonText {...createTextProps(color)}>{text}</CommonText>
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    align-items: center;
    gap: 0.2rem;
`;

const createTextProps = (color: string) => {
    const textProps = {
        TextTag: 'span',
        fontWeight: 'bold',
        fontSize: 'body',
        color,
    } as const;
    return textProps;
};
