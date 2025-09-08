import { css } from '@emotion/react';

import ToggleButton from '../../atoms/Button/ToggleButton';
import Icon from '../../atoms/Icon/Icons';
import SelectorTitleText from '../../atoms/Text/SelectorTitle';

import { theme } from '../../../theme/theme';

interface PropsState {
    time: number;
    isToggleOn: boolean;
    onToggle: () => void;
}

export default function TimeSelectorHeader({
    time,
    isToggleOn,
    onToggle,
}: PropsState) {
    return (
        <div css={headerStyles}>
            <div>
                <SelectorTitleText>출발 시간 선택</SelectorTitleText>
                <span css={courseTimeStyles}>{`왕복${time * 2}시간 코스`}</span>
            </div>
            <div>
                <SelectorTitleText>고도 보정하기</SelectorTitleText>
                <div css={tooltipWrapper}>
                    <Icon
                        name='info-circle'
                        width={1.5}
                        height={1.5}
                        color='grey-100'
                    />
                    <span>
                        {
                            '코스의 정상 고도를 반영해,\n기온과 풍속을 알려드려요.'
                        }
                    </span>
                </div>
                <ToggleButton isOn={isToggleOn} onClick={onToggle} />
            </div>
        </div>
    );
}

const { colors, typography } = theme;

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

const tooltipWrapper = css`
    position: relative;
    display: inline-block;

    & span {
        visibility: hidden;
        opacity: 0;
        width: 12rem;
        background-color: ${colors.grey[100]};
        color: ${colors.grey[0]};
        text-align: center;
        border-radius: 0.5rem;
        padding: 0.5rem;
        position: absolute;
        z-index: 99;
        bottom: 125%;
        left: 50%;
        transform: translateX(-50%);
        transition: opacity 0.3s;
        font-size: ${typography.fontSize.caption};
        white-space: pre-wrap;
        line-height: 1.4;
    }

    &:hover span,
    &:focus span {
        visibility: visible;
        opacity: 1;
    }
`;
