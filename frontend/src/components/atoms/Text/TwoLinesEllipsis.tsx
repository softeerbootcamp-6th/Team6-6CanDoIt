import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    children: React.ReactNode;
}

export default function TwoLinesEllipsis(props: propsState) {
    const { children } = props;
    return <p css={commentStyle}>{children}</p>;
}

const { colors, typography } = theme;

const commentStyle = css`
    width: 15.625rem;
    height: 2.875rem;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    word-break: break-all;

    line-height: 150%;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.medium};
    color: ${colors.grey[50]};
`;
