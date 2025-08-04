import styles from './Button.module.scss';

interface propsState {
    color?: string;
    backgroundColor?: string;
    width?: string;
    height?: string;
    borderRadius?: string;
    border?: string;
    fontSize?: string;
    fontWeight?: string;
    children?: React.ReactNode;
    onClick?: () => void;
}

export default function Button(props: propsState) {
    const {
        color = 'grey-0',
        backgroundColor = 'grey-100',
        width = '100%',
        height = '100%',
        borderRadius = '0',
        border = 'none',
        fontSize = 'caption',
        fontWeight = 'medium',
        children,
        onClick,
    } = props;

    return (
        <button
            className={`text-${color} bg-${backgroundColor} fontSize-${fontSize} fontWeight-${fontWeight} ${styles['button']}`}
            style={{
                width: width,
                height: height,
                borderRadius: borderRadius,
                border: border,
            }}
            onClick={onClick}
        >
            {children}
        </button>
    );
}
