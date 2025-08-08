import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import MultiLocationTemperature from '../MultiLocationTemperature/MultiLocationTemperature';
import Icon from '../../atoms/Icon/Icons';

interface PropsState {
    time: string;
    dist: number;
}

export default function MountainInfoPreview({ time, dist }: PropsState) {
    const weatherData = createWeatherData({
        surfaceTemperature: 12,
        summitTemperature: 20,
    });

    return (
        <div css={wrapperStyles}>
            <div css={dummySteyls} />

            <p css={textWrapperStyles}>
                <CommonText {...WhiteTextProps}>{`${time}`}</CommonText>
                <CommonText {...TextProps}>동안</CommonText>
                <CommonText {...WhiteTextProps}>{`${dist}Km`}</CommonText>
                <CommonText {...TextProps}>를 올라야해요</CommonText>
            </p>
            <div css={lineStyles}>
                <Icon
                    name='clear-day'
                    color='grey-100'
                    width={1.5}
                    height={1.5}
                />
                <MultiLocationTemperature data={weatherData} />
            </div>
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2.2rem;
    width: 38rem;
    height: 50dvh;
    margin-top: 2%;
`;

const textWrapperStyles = css`
    & :nth-of-type(-n + 3) {
        margin-left: 0.4rem;
    }
`;

const lineStyles = css`
    display: flex;
    align-items: center;
    justify-content: center;
    & > svg {
        margin-right: 0.5rem;
    }
`;

const dummySteyls = css`
    width: 100%;
    flex-grow: 1;
    display: flex;
    justify-content: center;
    background: red;
`;

const WhiteTextProps = {
    TextTag: 'span',
    color: 'grey-100',
    fontWeight: 'bold',
    fontSize: 'body',
} as const;

const TextProps = {
    TextTag: 'span',
    color: 'grey-90',
    fontWeight: 'bold',
    fontSize: 'body',
} as const;

function createWeatherData({
    surfaceTemperature,
    summitTemperature,
}: {
    surfaceTemperature: number;
    summitTemperature: number;
}) {
    return [
        {
            location: '지표면 온도',
            temperature: surfaceTemperature,
        },
        {
            location: '정상 온도',
            temperature: summitTemperature,
        },
    ];
}
