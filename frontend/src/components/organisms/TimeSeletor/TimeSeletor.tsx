import WeatherCell from '../../molecules/WeatherCell/WeatherCell';
import ToggleButton from '../../atoms/ToggleButton/ToggleButton';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import SelectorTitleText from '../../atoms/Text/SelectorTitle';
import Icon from '../../atoms/Icon/Icons';

const dummy = [
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
    '_',
];

const { colors, typography } = theme;

export default function TimeSeletor() {
    let size = 5;

    const dynamicScrollSizeStyles = css`
        width: ${size * 5}rem;
    `;
    return (
        <div css={timeSeletorStyles}>
            <div css={headerStyles}>
                <div>
                    <SelectorTitleText>출발 시간 선택</SelectorTitleText>
                    <span css={courseTimeStyles}>4시간 코스</span>
                </div>
                <div>
                    <SelectorTitleText>고도 보정하기</SelectorTitleText>
                    <Icon
                        name='info-circle'
                        width={1.5}
                        height={1.5}
                        color='grey-100'
                    />
                    <ToggleButton />
                </div>
            </div>
            <div css={contentWrapperStyles}>
                <div
                    css={css`
                        height: '2.1rem';
                    `}
                >
                    <div css={[scrollStyles, dynamicScrollSizeStyles]}>
                        <CommonText TextTag='span'>1AM</CommonText>
                        <CommonText TextTag='span'>9PM</CommonText>
                    </div>
                </div>

                <div
                    css={css`
                        display: flex;
                        margin: auto;
                    `}
                >
                    {dummy.map(() => (
                        <WeatherCell
                            time='2AM'
                            iconName='clear-day'
                            temperature={20}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
}

const timeSeletorStyles = css`
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 0.75rem;
    box-sizing: border-box;

    width: 87rem;
    padding: 0.5rem 1.5rem 1.5rem 1.5rem;

    border-radius: 1.5rem;
    border: 1px solid ${colors.greyOpacityWhite[80]};
    background: ${colors.greyOpacityWhite[70]};
    backdrop-filter: blur(50px);
`;

const headerStyles = css`
    display: flex;
    justify-content: space-between;
    padding: 0.625rem 1rem;
    box-sizing: border-box;
    height: 3.2rem;
    width: 100%;
    border-bottom: 1px solid ${colors.greyOpacityWhite[80]};
    z-index: 20;

    & > div {
        display: flex;
        align-items: center;
        gap: 0.4rem;
    }
`;

const courseTimeStyles = css`
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.medium};
    line-height: 150%;
    color: ${colors.grey[90]};

    background-color: ${colors.greyOpacityWhite[80]};
    padding: 0.1rem 0.4rem;
    margin-left: 0.2rem;

    border-radius: 0.375rem;
    border: 1px solid ${colors.greyOpacityWhite[90]};
`;
const scrollStyles = css`
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: ${colors.greyOpacityWhite[80]};
    border-radius: 2rem;

    & span {
        position: relative;
        padding: 0 0.8rem;

        height: 2rem;

        background-color: ${colors.greyOpacityWhite[70]};

        border-radius: 2rem;
        line-height: 150%;

        z-index: 10;
    }
`;

const contentWrapperStyles = css`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;
