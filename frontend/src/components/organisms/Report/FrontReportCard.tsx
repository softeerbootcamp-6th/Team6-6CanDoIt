import LabelList from '../../molecules/ReportCard/LabelList.tsx';
import ReportCardWrapper from './ReportCardWrapper.tsx';
import FrontCardFooter from '../../molecules/ReportCard/FrontCardFooter.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface PropsState {
    comment: string;
    timeAgo: string;
    filterLabels: string[];
    imgSrc?: string;
    imgAlt?: string;
    onClick: () => void;
}

export default function FrontReportCard(props: PropsState) {
    const {
        comment,
        timeAgo,
        filterLabels,
        imgSrc = '',
        imgAlt = '',
        onClick,
    } = props;
    return (
        <ReportCardWrapper onClick={onClick}>
            <img src={imgSrc} alt={imgAlt} css={reportImgStyle} />
            <LabelList labels={filterLabels} />
            <FrontCardFooter timeAgo={timeAgo} comment={comment} />
        </ReportCardWrapper>
    );
}

const { colors } = theme;

const reportImgStyle = css`
    width: 24rem;
    height: 27.9375rem;
    border-radius: 2rem;
    background-color: ${colors.grey[80]};
`;
