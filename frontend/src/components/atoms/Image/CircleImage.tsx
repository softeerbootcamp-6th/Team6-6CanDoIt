import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    size?: string;
    src?: string;
    alt?: string;
}

export default function CircleImage({
    size = '2.5rem',
    src = '',
    alt = '',
}: PropsState) {
    return <img src={src} alt={alt} css={reporterStyle({ size })} />;
}

const { colors } = theme;

const reporterStyle = ({ size }: { size: string }) => css`
    width: ${size};
    height: ${size};
    border-radius: 50%;
    background-color: ${colors.grey[80]};
`;
