import Icon from '../../atoms/Icon/Icons.tsx';
import MultiLocationTemperature from '../MultiLocationTemperature/MultiLocationTemperature.tsx';
import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    weatherIconName: string;
    surfaceTemperature: number;
    summitTemperature: number;
}

export default function MountainCardHeader(props: propsState) {
    const { weatherIconName, surfaceTemperature, summitTemperature } = props;

    const weatherData = createWeatherData({
        surfaceTemperature,
        summitTemperature,
    });

    const weatherIconProps = createWeatherIconProps({
        weatherIconName,
    });

    return (
        <div css={cardHeaderStyle}>
            <Icon {...weatherIconProps} />
            <MultiLocationTemperature data={weatherData} />
            <button css={arrowButtonStyle}>
                <Icon {...arrowButtonIconProps} />
            </button>
        </div>
    );
}

const { colors } = theme;

const arrowButtonIconProps = {
    name: 'arrow-narrow-right',
    width: 1.5,
    height: 1.25,
    color: 'grey-0',
} as const;

const cardHeaderStyle = css`
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    gap: 0.5rem;
`;

const arrowButtonStyle = css`
    margin-left: auto;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 50%;
    border: none;
    background-color: ${getColor({ colors, colorString: 'grey-100' })};
    cursor: pointer;
`;

function createWeatherData({
    surfaceTemperature,
    summitTemperature,
}: {
    surfaceTemperature: number;
    summitTemperature: number;
}) {
    return [
        {
            location: '지표면',
            temperature: surfaceTemperature,
        },
        {
            location: '정상',
            temperature: summitTemperature,
        },
    ];
}

function createWeatherIconProps({
    weatherIconName,
}: {
    weatherIconName: string;
}) {
    return {
        name: weatherIconName,
        width: 1.5,
        height: 1.5,
        color: 'grey-100',
    } as const;
}
