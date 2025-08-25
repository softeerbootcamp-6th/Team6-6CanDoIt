import CommonText from '../../atoms/Text/CommonText.tsx';

interface LocationTemperature {
    location: string;
    temperature: number;
}

interface MultiLocationProps {
    data: LocationTemperature[];
    separator?: string;
}

export default function MultiLocationTemperature({
    data,
    separator = ' | ',
}: MultiLocationProps) {
    return (
        <>
            {data.map(({ location, temperature }, idx) => (
                <LocationTemperatureItem
                    location={location}
                    temperature={temperature}
                    isLast={idx === data.length - 1}
                    separator={separator}
                />
            ))}
        </>
    );
}

function LocationTemperatureItem({
    location,
    temperature,
    isLast,
    separator,
}: {
    location: string;
    temperature: number;
    isLast: boolean;
    separator: string;
}) {
    return (
        <>
            <CommonText {...greyTextProps}>{`${location} `}</CommonText>
            <CommonText {...boldTempProps}>{`${temperature}°C`}</CommonText>
            {!isLast && <CommonText {...greyTextProps}>{separator}</CommonText>}
        </>
    );
}

const greyTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'regular',
    color: 'greyOpacityWhite-50',
} as const;

const boldTempProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'grey-100',
} as const;
