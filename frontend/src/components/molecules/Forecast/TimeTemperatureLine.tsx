import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';

interface PropsState {
    lineData: { title: string; temperature: number; wind: number };
}

export default function TimeTimperatureLine({ lineData }: PropsState) {
    const { title, temperature, wind } = lineData;
    return (
        <div css={lineStyles}>
            <CommonText
                TextTag='span'
                fontSize='body'
                fontWeight='bold'
                color='grey-0'
            >
                {title}
            </CommonText>
            <CommonText
                TextTag='span'
                fontSize='headline'
                fontWeight='medium'
                color='grey-20'
            >
                {`${temperature}°C`}
            </CommonText>
            <CommonText
                TextTag='span'
                fontSize='headline'
                fontWeight='medium'
                color='grey-20'
            >
                {`${wind}m/s`}
            </CommonText>
        </div>
    );
}
const lineStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    & span:first-of-type {
        flex: 0.5;
    }

    & span:not(:first-of-type) {
        flex: 1;
    }

    & span {
        text-align: left;
    }
`;
