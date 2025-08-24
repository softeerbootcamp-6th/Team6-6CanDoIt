import { css } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';

interface PropsState {
    text: string;
    iconName: string;
    color: string;
    fontSize?: FontSize;
}

type FontSize = 'body' | 'caption' | 'label' | 'title' | 'display' | 'headline';

export default function TextWithIcon({
    text,
    iconName,
    color,
    fontSize = 'body',
}: PropsState) {
    return (
        <div css={wrapperStyles}>
            <Icon name={iconName} width={1.5} height={1.5} color={color} />
            <CommonText {...createTextProps(color, fontSize)}>
                {text}
            </CommonText>
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    align-items: center;
    gap: 0.2rem;
`;

const createTextProps = (color: string, fontSize: FontSize = 'body') => {
    const textProps = {
        TextTag: 'span',
        fontWeight: 'bold',
        fontSize,
        color,
    } as const;
    return textProps;
};
