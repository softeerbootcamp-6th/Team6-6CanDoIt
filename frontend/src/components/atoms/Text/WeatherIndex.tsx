import type { FontSizeType } from '../../../types/themeTypes';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface WeatherIdx {
    color: string;
    backGroundColor: string;
    height: string;
    padding: string;
    fontSize: FontSizeType;
    text: string;
}

interface PropsState {
    type: WeatherType;
}
type WeatherType = '매우좋음' | '좋음' | '보통' | '나쁨';
const { colors, typography } = theme;

function WeatherIndex(props: WeatherIdx) {
    const { color, backGroundColor, height, padding, fontSize, text } = props;

    return (
        <div
            css={[
                wrapperStyles,
                createDynamicStyles(
                    backGroundColor,
                    padding,
                    height,
                    color,
                    fontSize,
                ),
            ]}
        >
            {`등산지수 ${text}`}
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    justify-content: center;
    align-items: center;

    width: max-content;
    box-sizing: border-box;
    border-radius: 6.25rem;
`;

const createDynamicStyles = (
    backGroundColor: string,
    padding: string,
    height: string,
    color: string,
    fontSize: FontSizeType,
) => {
    return css`
        font-size: ${typography.fontSize[fontSize]};
        font-weight: ${typography.fontWeight.bold};
        background-color: ${backGroundColor};
        padding: ${padding};
        height: ${height};
        color: ${color};
    `;
};

function WeatherIndexVivid(props: PropsState) {
    const { type } = props;
    let backGroundColor;
    let color;

    switch (type) {
        case '매우좋음':
            color = colors.status.light.excellent;
            backGroundColor = colors.status.normal.excellent;
            break;
        case '좋음':
            color = colors.status.light.good;
            backGroundColor = colors.status.normal.good;
            break;
        case '보통':
            color = colors.status.light.average;
            backGroundColor = colors.status.normal.average;
            break;
        case '나쁨':
            color = colors.status.light.bad;
            backGroundColor = colors.status.normal.bad;
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
            color = colors.status.normal.excellent;
            backGroundColor = colors.status.regular.excellent;
            break;
        case '좋음':
            color = colors.status.normal.good;
            backGroundColor = colors.status.regular.good;
            break;
        case '보통':
            color = colors.status.normal.average;
            backGroundColor = colors.status.regular.average;
            break;
        case '나쁨':
            color = colors.status.normal.bad;
            backGroundColor = colors.status.regular.bad;
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
