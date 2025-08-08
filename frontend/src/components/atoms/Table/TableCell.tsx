import { css } from '@emotion/react';

interface PropsState {
    children: React.ReactNode;
}

export default function TableCell({ children }: PropsState) {
    return <td css={tdstyles}>{children}</td>;
}

const tdstyles = css`
    padding-left: 1rem;
`;
