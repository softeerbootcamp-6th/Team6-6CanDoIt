import { css } from '@emotion/react';

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
    return (
        <img
            src={src}
            alt={alt}
            css={reporterStyle({ size })}
            draggable={false}
        />
    );
}

const reporterStyle = ({ size }: { size: string }) => css`
    width: ${size};
    height: ${size};
    border-radius: 50%;
    object-fit: cover;
`;
