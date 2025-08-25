import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import BackWeathercardDetailInfoLine from '../../molecules/Forecast/BackWeatherCardDetailInfoLine';

interface PropsState {
    detailData: {
        apparentTemperature: DetailData;
        precipitationProbability: DetailData;
        sky: DetailData;
        humidity: DetailData;
    };
}

interface DetailData {
    title: string;
    iconName: string;
    value: string[] | number[];
}

export default function BackWeatherCardDetailInfoColumns({
    detailData,
}: PropsState) {
    return (
        <div css={wrapperStyles}>
            {Object.values(detailData).map((item) => (
                <BackWeathercardDetailInfoLine key={item.title} data={item} />
            ))}
        </div>
    );
}

const { colors } = theme;

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    border-top: 0.5px solid ${colors.grey[90]};
    padding-top: 1rem;
`;
