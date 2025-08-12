import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    size?: string;
}

export default function ReporterImage({ size = '2.5rem' }: propsState) {
    return <div css={reporterStyle({ size })} />;
}

const { colors } = theme;

const reporterStyle = ({ size }: { size: string }) => css`
    width: ${size};
    height: ${size};
    border-radius: 50%;
    background-color: ${colors.grey[80]};
`;
