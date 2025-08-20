import { css } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons.tsx';
import SelectorText from '../../atoms/Text/SelectorText.tsx';

interface PropsState {
    time: string;
    temperature: number;
    iconName: string;
}

export default function WeatherCell({
    time,
    temperature,
    iconName,
}: PropsState) {
    return (
        <div css={wrapperStyles}>
            <SelectorText>{time}</SelectorText>
            <Icon
                name={iconName}
                width={1.5}
                height={1.5}
                color='grey-80'
                opacity={0.8}
            />
            <SelectorText>{`${temperature}°C`}</SelectorText>
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    flex: 0 0 auto;
    scroll-snap-align: start;
    width: 5rem;
    height: 7.875rem;
`;
