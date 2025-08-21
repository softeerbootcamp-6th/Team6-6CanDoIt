import { css } from '@emotion/react';
import MultiLocationTemperature from '../Text/MultiLocationTemperature';
import Icon from '../../atoms/Icon/Icons';
import CloseButton from '../../atoms/Button/CloseButton';

interface PropsState {
    onClose: () => void;
    locationTemperatureList: LocationTemperature[];
}

type LocationTemperature = {
    location: string;
    temperature: number;
};

export default function FrontWeatherCardHeader({
    onClose,
    locationTemperatureList,
}: PropsState) {
    return (
        <div>
            <div
                css={css`
                    display: flex;
                    justify-content: space-between;
                `}
            >
                <div
                    css={css`
                        display: flex;
                        align-items: center;
                        gap: 0.5rem;
                    `}
                >
                    <Icon
                        name='clear-day'
                        width={1.5}
                        height={1.5}
                        color='grey-100'
                    />
                    <MultiLocationTemperature data={locationTemperatureList} />
                </div>
                <CloseButton onClose={onClose} />
            </div>
        </div>
    );
}
