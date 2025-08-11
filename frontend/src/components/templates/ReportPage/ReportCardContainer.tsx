import { HeadlineHeading } from '../../atoms/Heading/Heading.tsx';
import FrontReportCard from '../../organisms/ReportCard/FrontReportCard.tsx';
import { css } from '@emotion/react';
import ChipItem from '../../molecules/ChipItem/ChipItem.tsx';

type cardData = { comment: string; minutesAgo: number; filterLabels: string[] };

interface propsState {
    title: string;
    circleColor: string;
    cards: cardData[];
}

export default function ReportCardContainer(props: propsState) {
    const { title, circleColor, cards } = props;
    return (
        <>
            <div css={reportTitleStyle}>
                <HeadlineHeading HeadingTag='h2'>{title}</HeadlineHeading>
                <div css={circleStyle({ bg: circleColor })} />
                <ChipItem text='제보하기' iconName='edit-03' />
            </div>
            <div css={reportCardContainerStyle}>
                {cards.map((card) => (
                    <FrontReportCard
                        comment={card.comment}
                        minutesAgo={card.minutesAgo}
                        filterLabels={card.filterLabels}
                    />
                ))}
            </div>
        </>
    );
}

const reportCardContainerStyle = css`
    display: flex;
    gap: 1rem;
    margin-left: 2rem;
    margin-top: 2rem;
`;

const reportTitleStyle = css`
    width: max-content;
    margin-top: 3rem;
    margin-left: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 1rem;
`;

const circleStyle = ({ bg }: { bg: string }) => css`
    width: 1.25rem;
    height: 1.25rem;
    border-radius: 50%;
    background-color: ${bg};
`;
