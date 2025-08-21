import { css } from '@emotion/react';
import TimeTimperatureLine from '../../molecules/Forecast/TimeTemperatureLine';
import sidebarImg from '../../../assets/body-stepper.svg';
export default function BackWeatherCardTimeTemperature() {
    return (
        <div
            css={css`
                display: flex;
            `}
        >
            <div
                css={css`
                    margin: auto;
                    box-sizing: border-box;
                    padding-top: 2px;
                `}
            >
                <img src={sidebarImg} />
            </div>

            <div
                css={css`
                    display: flex;
                    flex-direction: column;
                    gap: 1.5rem;
                    flex: 1;
                `}
            >
                <TimeTimperatureLine />
                <TimeTimperatureLine />
                <TimeTimperatureLine />
            </div>
        </div>
    );
}
