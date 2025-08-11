import ReporterImage from '../../atoms/ReporterImage/ReporterImage.tsx';
import MinutesAgo from '../../molecules/MinutesAgo/MinutesAgo.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import HeartStat from '../../molecules/HeartStat/HeartStat.tsx';
import LabelList from '../../molecules/LabelList/LabelList.tsx';
import { css } from '@emotion/react';
import BaseCard, { type PropsState } from './ReportCard.tsx';

export default function BackReportCard(props: PropsState) {
    const { comment, minutesAgo, heartCount = 0, filterLabels } = props;
    return (
        <BaseCard>
            <div css={backReportCardHeaderStyle}>
                <ReporterImage size='3.75rem' />
                <MinutesAgo value={minutesAgo} />
            </div>
            <CommonText {...textProps}>{comment}</CommonText>
            <HeartStat count={heartCount} />
            <LabelList labels={filterLabels} isCut={false} />
        </BaseCard>
    );
}

const textProps = {
    TextTag: 'p',
    fontSize: 'label',
    fontWeight: 'medium',
    color: 'grey-70',
    lineHeight: 1.5,
} as const;

const backReportCardHeaderStyle = css`
    width: 23rem;
    height: 3.75rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
`;
