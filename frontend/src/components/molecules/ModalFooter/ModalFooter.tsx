import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { getColor } from '../../../utils/utils.ts';

interface propsState {
    firstButtonText: string;
    secondButtonText: string;
}

export default function ModalFooter(props: propsState) {
    const { firstButtonText, secondButtonText } = props;
    return (
        <div css={modalFooterStyle}>
            <button css={modalButtonStyle({ color: 'grey-80' })}>
                {firstButtonText}
            </button>
            <button css={modalButtonStyle({ color: 'grey-100' })}>
                {secondButtonText}
            </button>
        </div>
    );
}

const { colors, typography } = theme;

const modalFooterStyle = css`
    width: 100%;
    height: max-content;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 1.5rem 3.8125rem;
    box-sizing: border-box;
`;

const modalButtonStyle = ({ color }: { color: string }) => css`
    background: none;
    border: none;
    width: 11rem;
    height: 2.25rem;
    font-size: ${typography.fontSize.label};
    font-weight: ${typography.fontWeight.bold};
    color: ${getColor({ colors, colorString: color })};
`;
