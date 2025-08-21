import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';

export default function TimeTimperatureLine() {
    return (
        <div css={lineStyles}>
            <CommonText
                TextTag='span'
                fontSize='label'
                fontWeight='bold'
                color='grey-0'
            >
                시작
            </CommonText>
            <CommonText
                TextTag='span'
                fontSize='headline'
                fontWeight='medium'
                color='grey-20'
            >
                20°C
            </CommonText>
            <CommonText
                TextTag='span'
                fontSize='headline'
                fontWeight='medium'
                color='grey-20'
            >
                13m/s
            </CommonText>
        </div>
    );
}
const lineStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;
