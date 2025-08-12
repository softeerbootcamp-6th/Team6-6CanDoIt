import CommonText from '../../atoms/Text/CommonText.tsx';
import HeartStat from '../../molecules/HeartStat/HeartStat.tsx';
import LabelList from '../../molecules/LabelList/LabelList.tsx';
import ReportCardWrapper from './ReportCardWrapper.tsx';
import BackReportCardHeader from '../../molecules/ReportCardHeader/BackReportCardHeader.tsx';

interface propsState {
    comment: string;
    minutesAgo: number;
    heartCount?: number;
    filterLabels: string[];
}

export default function BackReportCard(props: propsState) {
    const { comment, minutesAgo, heartCount = 0, filterLabels } = props;
    return (
        <ReportCardWrapper>
            <BackReportCardHeader minutesAgo={minutesAgo} />
            <CommonText {...textProps}>{comment}</CommonText>
            <HeartStat count={heartCount} />
            <LabelList labels={filterLabels} isCut={false} />
        </ReportCardWrapper>
    );
}

const textProps = {
    TextTag: 'p',
    fontSize: 'label',
    fontWeight: 'medium',
    color: 'grey-70',
    lineHeight: 1.5,
} as const;
