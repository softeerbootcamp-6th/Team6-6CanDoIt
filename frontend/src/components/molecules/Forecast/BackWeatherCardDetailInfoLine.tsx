import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import Icon from '../../atoms/Icon/Icons';

interface PropsState {
    data: DetailData;
}

interface DetailData {
    title: string;
    iconName: string;
    value: string[] | number[];
}

export default function BackWeathercardDetailInfoLine({ data }: PropsState) {
    const { title, iconName, value } = data;

    function formatValueArrow(value: (string | number)[]): string {
        return value.join(' -> ');
    }

    return (
        <div css={lineStyles}>
            <div css={lineStyles}>
                <div css={iconWrapperStyles}>
                    <Icon
                        name={iconName}
                        width={1.5}
                        height={1.5}
                        color='grey-60'
                    />
                </div>
                <CommonText
                    TextTag='span'
                    fontSize='caption'
                    fontWeight='bold'
                    color='grey-30'
                >
                    {title}
                </CommonText>
            </div>
            <CommonText
                TextTag='span'
                fontSize='caption'
                fontWeight='medium'
                color='grey-70'
            >
                {formatValueArrow(value)}
            </CommonText>
        </div>
    );
}

const { colors } = theme;

const iconWrapperStyles = css`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 1.8rem;
    height: 1.8rem;
    margin-right: 0.5rem;
    border-radius: 100%;
    background-color: ${colors.grey[100]};
    border: 1px solid ${colors.greyOpacity[90]};
`;

const lineStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;
