// 산 이름, 날씨 정보를 받고 렌더링
import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
import MountainCardFooter from '../../molecules/MoutainCardFooter/MountainCardFooter.tsx';
import MountainCardHeader from '../../molecules/MountainCardHeader/MountainCardHeader.tsx';

interface propsState {
    mountainName: string;
    weatherIconName: string;
    surfaceTemperature: number;
    summitTemperature: number;
}

export default function MountainCard(props: propsState) {
    const {
        mountainName,
        weatherIconName,
        surfaceTemperature,
        summitTemperature,
    } = props;

    return (
        <div css={cardStyle}>
            <MountainCardHeader
                weatherIconName={weatherIconName}
                surfaceTemperature={surfaceTemperature}
                summitTemperature={summitTemperature}
            />
            <MountainCardFooter mountainName={mountainName} />
        </div>
    );
}

const { colors } = theme;

const cardStyle = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    min-width: 18.75rem;
    width: 18.75rem;
    height: 36.25rem;
    box-sizing: border-box;
    background: ${getColor({ colors, colorString: 'grey-50' })};
    border-radius: 1.5rem;
    padding: 1.25rem;
`;
