import LabelList from '../../molecules/ReportCard/LabelList.tsx';
import ReportCardWrapper from './ReportCardWrapper.tsx';
import FrontCardFooter from '../../molecules/ReportCard/FrontCardFooter.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

interface propsState {
    comment: string;
    minutesAgo: number;
    filterLabels: string[];
    imgSrc?: string;
    imgAlt?: string;
}

export default function FrontReportCard(props: propsState) {
    const {
        comment,
        minutesAgo,
        filterLabels,
        imgSrc = '',
        imgAlt = '',
    } = props;
    return (
        <ReportCardWrapper>
            <img src={imgSrc} alt={imgAlt} css={reportImgStyle} />
            <LabelList labels={filterLabels} />
            <FrontCardFooter minutesAgo={minutesAgo} comment={comment} />
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
