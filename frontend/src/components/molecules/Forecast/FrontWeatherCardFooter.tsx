import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';

export default function FrontWeatherCardFooter() {
    return (
        <div>
            <CommonText
                TextTag='span'
                fontSize='headline'
                fontWeight='bold'
                color='grey-100'
            >
                가야산
            </CommonText>
            <div css={wrapperStyles}>
                <CommonText
                    TextTag='span'
                    fontSize='body'
                    fontWeight='medium'
                    color='greyOpacityWhite-50'
                >
                    만물상 코스 8.9km
                </CommonText>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='medium'
                    color='greyOpacityWhite-50'
                >
                    4시간 30분
                </CommonText>
            </div>
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 0.6rem;
`;
