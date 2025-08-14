import MountainInfoPreview from '../../molecules/Forecast/MountainInfoPreview.tsx';
import { WeatherIndexLight } from '../../atoms/Text/WeatherIndex.tsx';
import { css } from '@emotion/react';
import ScrollIndicator from '../../molecules/Forecast/ScrollIndicator.tsx';

export default function SummaryInfoSection() {
    return (
        <div css={wrapperStyles}>
            <MountainInfoPreview time='0401' dist={5} />
            <WeatherIndexLight type='매우좋음' />
            <ScrollIndicator />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    align-items: center;
    height: calc(100dvh);
    justify-content: space-between;
`;
