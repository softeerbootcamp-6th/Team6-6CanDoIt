import { css } from '@emotion/react';
import TextWithIcon from '../Text/TextWithIcon.tsx';

interface PropsState {
    convertedIconName: string;
    weatherIconText: string;
    windSpeed: number;
}

export default function WeatherInfoColumn({
    convertedIconName,
    weatherIconText,
    windSpeed,
}: PropsState) {
    return (
        <span css={wrapperStyles}>
            <TextWithIcon
                iconName={convertedIconName}
                text={weatherIconText}
                color='greyOpacity-60'
                fontSize='caption'
            />
            <TextWithIcon
                iconName='wind'
                text={`${windSpeed}m/s`}
                color='greyOpacity-60'
                fontSize='caption'
            />
        </span>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
`;
