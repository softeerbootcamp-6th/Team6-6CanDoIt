import styles from './WeatherIndex.module.scss';
import Text from '../../atoms/Text/Text';

interface WeatherIdx {
    color: string;
    backGroundColor: String;
    height: string;
    padding: string;
    fontSize: string;
    text: string;
}

interface PropsState {
    type: WeatherType;
}
type WeatherType = '매우좋음' | '좋음' | '보통' | '나쁨';

function WeatherIndex(props: WeatherIdx) {
    const { color, backGroundColor, height, padding, fontSize, text } = props;
    return (
        <div
            className={`bg-${backGroundColor} ${styles['text-wrapper']}`}
            style={{ padding: padding, height: height }}
        >
            <Text
                textTag='span'
                fontSize={fontSize}
                fontWeight='bold'
                color={color}
            >
                {`등산지수 ${text}`}
            </Text>
        </div>
    );
}

function WeatherIndexVivid(props: PropsState) {
    const { type } = props;
    let backGroundColor;
    let color;

    switch (type) {
        case '매우좋음':
            color = 'status-light-excellent';
            backGroundColor = 'status-normal-excellent';
            break;
        case '좋음':
            color = 'status-light-good';
            backGroundColor = 'status-normal-good';
            break;
        case '보통':
            color = 'status-light-average';
            backGroundColor = 'status-normal-average';
            break;
        case '나쁨':
            color = 'status-light-bad';
            backGroundColor = 'status-normal-bad';
            break;
    }

    return (
        <WeatherIndex
            color={color}
            backGroundColor={backGroundColor}
            height='2.125rem'
            padding='0.5rem 1rem'
            fontSize='caption'
            text={type}
        />
    );
}

function WeatherIndexLight(props: PropsState) {
    const { type } = props;
    let color;
    let backGroundColor;

    switch (type) {
        case '매우좋음':
            color = 'status-normal-excellent';
            backGroundColor = 'status-regular-excellent';
            break;
        case '좋음':
            color = 'status-normal-good';
            backGroundColor = 'status-regular-good';
            break;
        case '보통':
            color = 'status-normal-average';
            backGroundColor = 'status-regular-average';
            break;
        case '나쁨':
            color = 'status-normal-bad';
            backGroundColor = 'status-regular-bad';
            break;
    }

    return (
        <WeatherIndex
            color={color}
            backGroundColor={backGroundColor}
            height='2.75rem'
            padding='0.625rem 1.25rem'
            fontSize='body'
            text={type}
        />
    );
}

export { WeatherIndexLight, WeatherIndexVivid };
