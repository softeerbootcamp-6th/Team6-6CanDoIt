import CircleImage from '../../atoms/Image/CircleImage.tsx';
import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText.tsx';

interface propsState {
    minutesAgo: number;
}

export default function BackReportCardHeader({ minutesAgo }: propsState) {
    return (
        <div css={backReportCardHeaderStyle}>
            <CircleImage size='3.75rem' />
            <CommonText {...minutesAgoProps}>{`${minutesAgo}분전`}</CommonText>
        </div>
    );
}

const minutesAgoProps = {
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
