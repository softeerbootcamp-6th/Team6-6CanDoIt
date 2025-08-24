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
    userImageUrl?: string;
    onClick: () => void;
}

export default function FrontReportCard(props: PropsState) {
    const {
        comment,
        timeAgo,
        filterLabels,
        imgSrc = '',
        imgAlt = '',
        userImageUrl = '',
        onClick,
    } = props;
    return (
        <ReportCardWrapper onClick={onClick}>
            <img
                src={imgSrc}
                alt={imgAlt}
                css={reportImgStyle}
                draggable={false}
            />
            <LabelList labels={filterLabels} />
            <FrontCardFooter
                timeAgo={timeAgo}
                comment={comment}
                userImageUrl={userImageUrl}
            />
        </ReportCardWrapper>
    );
}

const { colors } = theme;

const reportImgStyle = css`
    width: 24rem;
    height: 27.9375rem;
    border-radius: 2rem;
    background-color: ${colors.grey[80]};
    object-fit: cover;
`;
