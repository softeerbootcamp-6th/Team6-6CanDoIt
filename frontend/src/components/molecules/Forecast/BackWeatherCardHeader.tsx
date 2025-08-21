import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import { WeatherIndexVivid } from '../../atoms/Text/WeatherIndex';

export default function BackWeatherCardHeader() {
    return (
        <div css={wrapperStyles}>
            <div css={lineStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='label'
                    fontWeight='bold'
                    color='grey-30'
                >
                    7월 24일
                </CommonText>
                <WeatherIndexVivid type='매우좋음' />
            </div>
            <div css={lineStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='medium'
                    color='grey-70'
                >
                    가야산 만물코스
                </CommonText>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='medium'
                    color='grey-70'
                >
                    9AM - 12PM
                </CommonText>
            </div>
        </div>
    );
}

const lineStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const wrapperStyles = css`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;
