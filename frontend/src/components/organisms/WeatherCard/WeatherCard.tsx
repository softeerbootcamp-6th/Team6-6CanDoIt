import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';
import TextWithIconContainer from '../../molecules/TextWithIconContainer/TextWithIconContainer';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    title: string;
    weatherInfo: WeatherInfo;
}

interface WeatherInfo {
    weatherIconName: string;
    weatherIconText: string;
    windSpeed: number;
}

const { colors } = theme;

// 배경색을 동적으로 입혀주고 이름 등은 백엔드와 협의 후 로직짜서 수정해야할듯.
export default function WeatherCard({ title, weatherInfo }: PropsState) {
    const dynamicBackgoundStyle = css`
        background-color: ${colors.accentWeather.sunny};
    `;

    return (
        <div css={[cardStyles, dynamicBackgoundStyle]}>
            <div css={headerStyles}>
                <CommonText {...titleTextProps}>{title}</CommonText>
                <button>
                    <Icon {...iconProps} />
                </button>
            </div>
            <div css={footerStyles}>
                <TextWithIconContainer {...weatherInfo} />
                <CommonText {...temperatureTextProps}>20°</CommonText>
            </div>
        </div>
    );
}

const titleTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'greyOpacity-20',
} as const;

const iconProps = {
    name: 'narrow-right',
    color: 'greyOpacity-20',
    width: 2,
    height: 2,
} as const;

const temperatureTextProps = {
    TextTag: 'span',
    fontSize: 'display',
    fontWeight: 'medium',
    color: 'grey-0',
} as const;

const cardStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    box-sizing: border-box;
    width: 15rem;
    height: 10.5rem;

    padding: 0.75rem 0;

    border-radius: 1.9rem;
    border: 2px solid ${colors.greyOpacityWhite[80]};
`;

const headerStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 0.85rem 0 1rem;
`;

const footerStyles = css`
    display: flex;
    justify-content: space-between;
    margin: 0 0.75rem 0 1rem;
`;
