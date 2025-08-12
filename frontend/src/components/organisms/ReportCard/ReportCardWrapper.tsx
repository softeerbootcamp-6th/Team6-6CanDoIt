import { theme } from '../../../theme/theme.ts';
import { css } from '@emotion/react';

interface propsState {
    children: React.ReactNode;
}

export default function ReportCardWrapper({ children }: propsState) {
    return <div css={cardStyle}>{children}</div>;
}

const { colors } = theme;

const cardStyle = css`
    width: 25rem;
    height: 36.25rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 2.5rem;
    padding: 0.6rem;
    box-sizing: border-box;
    background-color: ${colors.grey[20]};
`;
