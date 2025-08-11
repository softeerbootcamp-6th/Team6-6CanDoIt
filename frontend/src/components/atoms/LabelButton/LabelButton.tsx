import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    children: React.ReactNode;
}

export default function LabelButton(props: propsState) {
    const { children } = props;
    return <button css={filterLabelTextStyle}>{children}</button>;
}

const { colors, typography } = theme;

const filterLabelTextStyle = css`
    width: max-content;
    padding: 0.25rem 0.75rem;
    box-sizing: border-box;
    border-radius: 0.5rem;
    background-color: ${colors.grey[40]};
    white-space: nowrap;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.bold};
    color: ${colors.grey[100]};
    border: none;
    cursor: pointer;
`;
