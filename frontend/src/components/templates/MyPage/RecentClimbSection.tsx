import { css } from '@emotion/react';
import useApiQuery from '../../../hooks/useApiQuery';
import { theme } from '../../../theme/theme';

import ReportPendingModal from '../../molecules/Modal/ReportPendingModal';
import { LabelHeading } from '../../atoms/Heading/Heading';
import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';

import { formatDate } from '../Forecast/helpers';

interface RecentClimbData {
    id: number;
    courseId: number;
    forecastDate: string;
    updatedAt: string;
    mountainName: string;
    courseName: string;
}

interface PropsState {
    onClick: (courseId: number, forecastDate: string) => void;
}

export default function RecentClimbSection({ onClick }: PropsState) {
    const { data: recentClimbData, isLoading } = useApiQuery<RecentClimbData[]>(
        `/card/interaction/history`,
        { pageSize: 5 },
        {
            retry: false,
        },
    );

    return (
        <div>
            <LabelHeading HeadingTag='h2'>최근 본 등산일정</LabelHeading>
            <div css={listStyles}>
                {recentClimbData?.map((item, index) => {
                    const { forecastDate, mountainName, courseName, courseId } =
                        item;
                    if (isLoading) return <ReportPendingModal />;

                    return (
                        <div
                            css={wrapperStyles}
                            key={index}
                            onClick={() => onClick(courseId, forecastDate)}
                        >
                            <div css={headerStyles}>
                                <CommonText
                                    TextTag='span'
                                    fontSize='caption'
                                    fontWeight='medium'
                                    color='grey-80'
                                >
                                    {formatDate(forecastDate)}
                                </CommonText>
                                <div css={narrowRightStyles}>
                                    <Icon
                                        name='narrow-right'
                                        width={2}
                                        height={2}
                                        color='grey-0'
                                    />
                                </div>
                            </div>
                            <div css={contentWrapper}>
                                <CommonText
                                    TextTag='span'
                                    fontSize='body'
                                    fontWeight='bold'
                                    color='grey-100'
                                >
                                    {mountainName}
                                </CommonText>
                                <CommonText
                                    TextTag='span'
                                    fontSize='body'
                                    fontWeight='bold'
                                    color='grey-100'
                                >
                                    {courseName}
                                </CommonText>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

const { colors } = theme;

const wrapperStyles = css`
    height: 8rem;
    width: 18%;
    background-color: ${colors.grey[20]};
    border-radius: 2rem;
    padding: 0.5rem 1rem 1rem 1rem;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    cursor: pointer;

    &:hover {
        transform: translateY(-10px) scale(1.1);
        background-color: ${colors.grey[40]};
    }
`;

const listStyles = css`
    margin-top: 1.5rem;
    display: flex;
    gap: 3%;
`;

const contentWrapper = css`
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
`;

const narrowRightStyles = css`
    all: unset;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2.5rem;
    height: 2.5rem;
    border-radius: 100%;
    background-color: ${colors.grey[90]};
`;

const headerStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;
