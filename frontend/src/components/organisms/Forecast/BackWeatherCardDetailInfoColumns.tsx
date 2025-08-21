import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import BackWeathercardDetailInfoLine from '../../molecules/Forecast/BackWeatherCardDetailInfoLine';

export default function BackWeatherCardDetailInfoColumns() {
    return (
        <div css={wrapperStyles}>
            <BackWeathercardDetailInfoLine />
            <BackWeathercardDetailInfoLine />
            <BackWeathercardDetailInfoLine />
            <BackWeathercardDetailInfoLine />
            <BackWeathercardDetailInfoLine />
            <BackWeathercardDetailInfoLine />
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
