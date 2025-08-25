import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import WeatherInfoColumn from '../../molecules/Forecast/WeatherInfoColumn.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import {
    convertToIconName,
    covertToWeatherByIconName,
} from '../../../utils/utils.ts';

interface PropsState {
    title: string;
    weatherIconName: string;
    weatherIconText: string;
    windSpeed: number;
    temperature: number;
    courseAltitude?: number;
    precipitationType: string;
    onClick: (
        backgroundType: Background,
        title: string,
        courseAltitude?: number,
    ) => void;
}

type Background = 'sunny' | 'cloudy' | 'snow' | 'rain';

const { colors } = theme;

export default function WeatherCard({
    title,
    weatherIconName,
    weatherIconText,
    windSpeed,
    temperature,
    courseAltitude,
    precipitationType,
    onClick,
}: PropsState) {
    const convertedIconName = convertToIconName({
        precipitationType,
        sky: weatherIconName,
    });

    const backgroundType: Background =
        covertToWeatherByIconName(convertedIconName);

    const dynamicBackgoundStyle = css`
        background-color: ${colors.accentWeather[backgroundType]};
    `;

    const weatherInfo = { convertedIconName, weatherIconText, windSpeed };

    return (
        <div css={[cardStyles, dynamicBackgoundStyle]}>
            <div css={headerStyles}>
                <div css={titleStyles}>
                    <CommonText {...titleTextProps}>{title}</CommonText>
                    {courseAltitude && (
                        <CommonText {...subTitleTextProps}>
                            {`${courseAltitude}m`}
                        </CommonText>
                    )}
                </div>
                <button
                    css={buttonStyles}
                    onClick={() =>
                        onClick(backgroundType, title, courseAltitude)
                    }
                >
                    <Icon {...iconProps} />
                </button>
            </div>
            <div css={footerStyles}>
                <WeatherInfoColumn {...weatherInfo} />
                <CommonText {...temperatureTextProps}>
                    {temperature}°C
                </CommonText>
            </div>
        </div>
    );
}

const titleTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'greyOpacity-20',
} as const;
const subTitleTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'greyOpacity-60',
} as const;

const iconProps = {
    name: 'narrow-right',
    color: 'greyOpacity-20',
    width: 2,
    height: 2,
} as const;

const titleStyles = css`
    display: flex;
    gap: 0.5rem;
`;

const temperatureTextProps = {
    TextTag: 'span',
    fontSize: 'headline',
    fontWeight: 'medium',
    color: 'grey-0',
} as const;

const cardStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    box-sizing: border-box;
    width: 15rem;
    height: 9rem;

    padding: 0.75rem 0;

    border-radius: 1.9rem;
    border: 2px solid ${colors.greyOpacityWhite[80]};
`;

const buttonStyles = css`
    all: unset;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 3rem;
    height: 3rem;
    border-radius: 100%;
    background-color: ${colors.grey[100]};

    &:hover {
        cursor: pointer;
    }
`;

const headerStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 0.85rem 0 1rem;
`;

const footerStyles = css`
    display: flex;
    justify-content: space-between;
    margin: 0 0.75rem 0 1rem;

    & > span {
        line-height: 150%;
    }
`;
