import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import Icon from '../../atoms/Icon/Icons';

export default function BackWeathercardDetailInfoLine() {
    return (
        <div css={lineStyles}>
            <div css={lineStyles}>
                <div css={iconWrapperStyles}>
                    <Icon
                        name='clear-day'
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
                    체감온도
                </CommonText>
            </div>
            <CommonText
                TextTag='span'
                fontSize='caption'
                fontWeight='medium'
                color='grey-70'
            >
                value value value
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
