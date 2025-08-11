import ReportImage from '../../atoms/ReportImage/ReportImage.tsx';
import LabelList from '../../molecules/LabelList/LabelList.tsx';
import ReporterImage from '../../atoms/ReporterImage/ReporterImage.tsx';
import CardCommentEllipsis from '../../molecules/CardCommentEllipsis/CardCommentEllipsis.tsx';
import MinutesAgo from '../../molecules/MinutesAgo/MinutesAgo.tsx';
import { css } from '@emotion/react';
import BaseCard, { type PropsState } from './ReportCard.tsx';

export default function FrontReportCard(props: PropsState) {
    const { comment, minutesAgo, filterLabels } = props;
    return (
        <BaseCard>
            <ReportImage />
            <LabelList labels={filterLabels} />
            <div css={frontCardFooterStyle}>
                <ReporterImage />
                <CardCommentEllipsis>{comment}</CardCommentEllipsis>
                <MinutesAgo value={minutesAgo} />
            </div>
        </BaseCard>
    );
}

const frontCardFooterStyle = css`
    width: 23rem;
    height: 2.875rem;
    display: flex;
    justify-content: space-between;
`;
