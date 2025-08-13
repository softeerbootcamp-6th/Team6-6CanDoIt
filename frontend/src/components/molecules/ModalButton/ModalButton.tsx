import { getColor } from '../../../utils/utils.ts';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    textColor?: string;
    children: React.ReactNode;
}

export default function ModalButton(props: PropsState) {
    const { textColor = 'grey-100', children } = props;
    return (
        <div css={buttonContainerStyle}>
            <button css={modalButtonStyle({ color: textColor })}>
                {children}
            </button>
        </div>
    );
}

const { colors, typography } = theme;

const buttonContainerStyle = css`
    width: 50%;
    display: flex;
    justify-content: center;
`;

const modalButtonStyle = ({ color }: { color: string }) => css`
    background: none;
    border: none;
    width: 11rem;
    height: 2.25rem;
    font-size: ${typography.fontSize.label};
    font-weight: ${typography.fontWeight.bold};
    color: ${getColor({ colors, colorString: color })};
    cursor: pointer;
`;
