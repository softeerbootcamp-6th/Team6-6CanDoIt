import PATH from '../../../assets/iconPath.ts';

interface PropsState {
    name: string;
    width: number;
    height: number;
    color: string;
    opacity?: number;
}

export default function Icon(props: PropsState) {
    const { name, opacity, color, width, height } = props;

    const pathData = PATH[name];
    if (!pathData) return null;

    return (
        <svg
            width={`${width}rem`}
            height={`${height}rem`}
            viewBox={`0 0 ${width * 16} ${height * 16}`}
            fill='none'
            xmlns='http://www.w3.org/2000/svg'
        >
            <path
                stroke={`var(--${color})`}
                d={pathData}
                strokeOpacity={opacity || 1}
                strokeWidth='2'
                strokeLinecap='round'
                strokeLinejoin='round'
            ></path>
        </svg>
    );
}
