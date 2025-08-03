import Temperature from '../../atoms/Temperature/Temperature.tsx';

interface propsState {
    description: string;
    temperature: number;
    descriptionFontSize?: string;
    descriptionFontWeight?: string;
    descriptionTextColor?: string;
    temperatureTextColor?: string;
    temperatureFontSize?: string;
    temperatureFontWeight?: string;
}

export default function WeatherContent(props: propsState) {
    const {
        description,
        temperature,
        descriptionFontSize = 'caption',
        descriptionFontWeight = 'regular',
        descriptionTextColor = 'text-grey-opacity-50',
        temperatureFontWeight = 'bold',
        temperatureFontSize = 'caption',
        temperatureTextColor = 'text-grey-100',
    } = props;

    return (
        <>
            <span
                className={`fontSize-${descriptionFontSize} fontWeight-${descriptionFontWeight} ${descriptionTextColor}`}
            >
                {description}
            </span>
            <Temperature
                temperature={temperature}
                color={temperatureTextColor}
                fontSize={temperatureFontSize}
                fontWeight={temperatureFontWeight}
            />
        </>
    );
}
