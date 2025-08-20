import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    onClick?: () => void;
}

export default function ToggleButton(props: PropsState) {
    const { onClick } = props;
    return (
        <div css={buttonWrapperStyles} onClick={() => onClick && onClick()}>
            <div css={circleStyles}></div>
        </div>
    );
}

const { colors } = theme;

const buttonWrapperStyles = css`
    display: flex;
    align-items: center;

    padding-left: 0.1rem;
    width: 3.2rem;
    height: 2rem;

    background-color: ${colors.grey[80]};
    border-radius: 1rem;
`;

const circleStyles = css`
    width: 1.8rem;
    height: 1.8rem;
    border-radius: 100%;

    background-color: ${colors.grey[40]};
`;
