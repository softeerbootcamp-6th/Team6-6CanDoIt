import { css } from '@emotion/react';

export default function Header() {
    return <div css={headerStyles}>headerSection</div>;
}

const headerStyles = css`
    height: 5rem;
`;
