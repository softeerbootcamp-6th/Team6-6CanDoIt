import { theme } from '../../../theme/theme.ts';
import { css } from '@emotion/react';

interface PropsState {
    onClick: () => void;
    children: React.ReactNode;
    isEmptyCard?: boolean;
}

export default function ReportCardWrapper({
    onClick,
    children,
    isEmptyCard = false,
}: PropsState) {
    return (
        <div onClick={onClick} css={cardStyle(isEmptyCard)}>
            {children}
        </div>
    );
}

const { colors } = theme;

const cardStyle = (isEmptyCard: boolean) => css`
    width: 25rem;
    height: 36.25rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-radius: 2.5rem;
    padding: 0.6rem;
    box-sizing: border-box;
    background-color: ${isEmptyCard ? colors.grey[0] : colors.grey[20]};
    border: ${isEmptyCard ? `5px dashed ${colors.grey[60]}` : 'none'};
    ${isEmptyCard &&
    `cursor: pointer;
     &:hover{
        background-color: ${colors.grey[10]};
        border: 5px dashed ${colors.grey[90]};
     }`}
`;
