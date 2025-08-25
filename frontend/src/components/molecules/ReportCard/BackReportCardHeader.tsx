import CircleImage from '../../atoms/Image/CircleImage.tsx';
import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText.tsx';

interface PropsState {
    timeAgo: string;
    userImageUrl?: string;
}

export default function BackReportCardHeader({
    timeAgo,
    userImageUrl,
}: PropsState) {
    return (
        <div css={backReportCardHeaderStyle}>
            <CircleImage src={userImageUrl} size='3.75rem' />
            <CommonText {...timeAgoProps}>{timeAgo}</CommonText>
        </div>
    );
}

const timeAgoProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'greyOpacityWhite-60',
    lineHeight: 1.3,
} as const;

const backReportCardHeaderStyle = css`
    width: 23rem;
    height: 3.75rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
`;
