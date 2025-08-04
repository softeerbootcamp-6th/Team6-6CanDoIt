import styles from './Icon.module.scss';
import PATH from '../../../assets/iconPath.ts';

interface PropsState {
    name: string;
    WrapperWidth: string;
    WrapperHeight: string;

    width: number;
    height: number;
    opacity?: number;
    color?: string;
    backgroundColor?: string;
    shape?: IconWrapperShape;
}

type IconWrapperShape = 'circle';

export default function Icon(props: PropsState) {
    const {
        name,
        opacity,
        color,
        WrapperHeight,
        WrapperWidth,
        backgroundColor,
        shape,
        width,
        height,
    } = props;

    const pathData = PATH[name];
    if (!pathData) return null;

    return (
        <div
            className={`${styles.iconWrapper} ${backgroundColor ? backgroundColor : ''} ${shape ? styles[shape] : ''}`}
            style={{
                width: WrapperWidth,
                height: WrapperHeight,
            }}
        >
            <svg
                width={`${width}rem`}
                height={`${height}rem`}
                viewBox={`0 0 ${width * 16} ${height * 16}`}
                fill='none'
                xmlns='http://www.w3.org/2000/svg'
            >
                <path
                    d={pathData}
                    stroke={color || 'white'}
                    strokeOpacity={opacity || 1}
                    strokeWidth='2'
                    strokeLinecap='round'
                    strokeLinejoin='round'
                ></path>
            </svg>
        </div>
    );
}
