import CommonText from '../../atoms/Text/CommonText.tsx';
import HeartStat from './HeartStat.tsx';
import LabelList from '../../molecules/ReportCard/LabelList.tsx';
import ReportCardWrapper from './ReportCardWrapper.tsx';
import BackReportCardHeader from '../../molecules/ReportCard/BackReportCardHeader.tsx';

interface PropsState {
    comment: string;
    timeAgo: string;
    likeCount?: number;
    filterLabels: string[];
    onHeartClick: () => void;
    onClick: () => void;
}

export default function BackReportCard(props: PropsState) {
    const {
        comment,
        timeAgo,
        likeCount = 0,
        filterLabels,
        onHeartClick,
        onClick,
    } = props;
    return (
        <ReportCardWrapper onClick={onClick}>
            <BackReportCardHeader timeAgo={timeAgo} />
            <CommonText {...textProps}>{comment}</CommonText>
            <HeartStat count={likeCount} onHeartClick={onHeartClick} />
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
