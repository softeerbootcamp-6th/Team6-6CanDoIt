import { css } from '@emotion/react';
import FrontWeatherCardHeader from '../../molecules/Forecast/FrontWeatherCardHaeder';
import FrontWeatherCardFooter from '../../molecules/Forecast/FrontWeatherCardFooter';

interface PropsState {
    onClose: () => void;
}

const data = [
    {
        location: '지표면',
        temperature: 20,
    },
    {
        location: '정상',
        temperature: 30,
    },
];

export default function FrontWeatherSummaryCard({ onClose }: PropsState) {
    return (
        <>
            <FrontWeatherCardHeader
                onClose={onClose}
                locationTemperatureList={data}
            />
            <div css={dummyStyles}></div>
            <FrontWeatherCardFooter />
        </>
    );
}

const dummyStyles = css`
    width: 100%;
    height: 60%;
    background-color: red;
`;
