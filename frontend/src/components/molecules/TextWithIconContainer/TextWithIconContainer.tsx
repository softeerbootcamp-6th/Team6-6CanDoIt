import { css } from '@emotion/react';
import TextWithIcon from '../TextWithIcon/TextWithIcon';

interface PropsState {
    weatherIconName: string;
    weatherIconText: string;
    windSpeed: number;
}

export default function TextWithIconContainer({
    weatherIconName,
    weatherIconText,
    windSpeed,
}: PropsState) {
    return (
        <span css={wrapperStyles}>
            <TextWithIcon
                iconName={weatherIconName}
                text={weatherIconText}
                color='greyOpacity-60'
            />
            <TextWithIcon
                iconName='wind'
                text={`${windSpeed}m/s`}
                color='greyOpacity-60'
            />
        </span>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
`;
