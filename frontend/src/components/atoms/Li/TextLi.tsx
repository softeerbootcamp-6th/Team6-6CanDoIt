import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

interface PropsState {
    children?: string;
}
export default function TextLi({ children }: PropsState) {
    return <li css={textStyles}>{children}</li>;
}

const { colors, typography } = theme;

const textStyles = css`
    color: ${colors.greyOpacityWhite[50]};
    font-size: ${typography.fontSize.label};
    font-weight: ${typography.fontWeight.bold};
    font-style: normal;
    line-height: normal;

    &:hover {
        color: ${colors.grey[100]};
        cursor: pointer;
    }
`;
