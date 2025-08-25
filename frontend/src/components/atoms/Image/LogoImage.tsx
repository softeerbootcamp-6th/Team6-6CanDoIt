import mainImg from '../../../assets/mainImg.png';

interface PropsState {
    width?: number;
    height?: number;
}

export default function LogoImage({ width = 115, height = 30 }: PropsState) {
    return <img src={mainImg} width={width} height={height} alt='Main Logo' />;
}
