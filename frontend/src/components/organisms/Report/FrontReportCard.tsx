import ReportImage from '../../atoms/ReportImage_delete/ReportImage.tsx';
import LabelList from '../../molecules/ReportCard/LabelList.tsx';
import ReportCardWrapper from './ReportCardWrapper.tsx';
import FrontCardFooter from '../../molecules/ReportCard/FrontCardFooter.tsx';

interface propsState {
    comment: string;
    minutesAgo: number;
    filterLabels: string[];
}

export default function FrontReportCard(props: propsState) {
    const { comment, minutesAgo, filterLabels } = props;
    return (
        <ReportCardWrapper>
            <ReportImage />
            <LabelList labels={filterLabels} />
            <FrontCardFooter minutesAgo={minutesAgo} comment={comment} />
        </ReportCardWrapper>
    );
}
