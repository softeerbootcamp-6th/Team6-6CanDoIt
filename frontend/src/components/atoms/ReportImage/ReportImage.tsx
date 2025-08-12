import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

export default function ReportImage() {
    return <div css={reportImgStyle} />;
}

const { colors } = theme;

const reportImgStyle = css`
    width: 24rem;
    height: 27.9375rem;
    border-radius: 2rem;
    background-color: ${colors.grey[80]};
`;
