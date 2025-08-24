import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    count: number;
    isLiked?: boolean;
    onHeartClick: () => void;
}

export default function HeartStat({
    count,
    isLiked = false,
    onHeartClick,
}: propsState) {
    return (
        <div css={heartStyle}>
            <button
                css={buttonStyle(isLiked)}
                onClick={(event) => {
                    event.stopPropagation();
                    onHeartClick();
                }}
            >
                <Icon {...heartIconProps(isLiked)} />
            </button>
            <CommonText TextTag='span' fontSize='body' fontWeight='bold'>
                {count}
            </CommonText>
        </div>
    );
}

const { colors } = theme;

const heartIconProps = (isLiked: boolean) => ({
    name: 'heart-rounded',
    fill: isLiked ? colors.primary.normal : 'none',
    width: 1.5,
    height: 1.5,
    color: isLiked ? 'grey-20' : 'grey-100',
});

const buttonStyle = (isLiked: boolean) => css`
    background: none;
    border: none;
    padding: 0;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    ${isLiked &&
    `
    transform: scale(1.3);
  `}
`;

const heartStyle = css`
    width: 100%;
    height: max-content;
    display: flex;
    align-items: center;
    justify-content: start;
    gap: 0.5rem;
    margin-top: 1rem;
    margin-bottom: auto;
`;
