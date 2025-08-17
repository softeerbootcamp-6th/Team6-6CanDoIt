import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
import MountainCardFooter from '../../molecules/MountainCard/MountainCardFooter.tsx';
import MountainCardHeader from '../../molecules/MountainCard/MountainCardHeader.tsx';
import type { SelectedMountainData } from '../../../types/mountainTypes';

interface PropsState {
    mountainName: string;
    mountainDescription: string;
    weatherIconName: string;
    surfaceTemperature: number;
    summitTemperature: number;
    onClick: (data: SelectedMountainData) => void;
}

export default function MountainCard(props: PropsState) {
    const {
        mountainName,
        mountainDescription,
        weatherIconName,
        surfaceTemperature,
        summitTemperature,
        onClick,
    } = props;

    return (
        <div
            css={cardStyle}
            onClick={() => onClick({ mountainName, mountainDescription })}
        >
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
