import PATH from '../../../assets/iconPath.ts';
import { theme } from '../../../theme/theme.ts';
import { getColor } from '../../../utils/utils.ts';

interface PropsState {
    name: string;
    width: number;
    height: number;
    color: string;
    opacity?: number;
    fill?: string;
    storkeWidth?: number;
}

const { colors } = theme;

export default function Icon(props: PropsState) {
    const {
        name,
        opacity,
        color,
        width,
        height,
        fill = 'none',
        storkeWidth = 2,
    } = props;

    const pathData = PATH[name];
    if (!pathData) return null;

    return (
        <svg
            width={`${width}rem`}
            height={`${height}rem`}
            viewBox={`0 0 ${width * 16} ${height * 16}`}
            fill={fill}
            xmlns='http://www.w3.org/2000/svg'
        >
            <path
                stroke={getColor({ colors, colorString: color })}
                d={pathData}
                strokeOpacity={opacity || 1}
                strokeWidth={storkeWidth}
                strokeLinecap='round'
                strokeLinejoin='round'
            ></path>
        </svg>
    );
}
