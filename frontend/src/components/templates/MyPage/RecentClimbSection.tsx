import { css } from '@emotion/react';
import useApiQuery from '../../../hooks/useApiQuery';
import { LabelHeading } from '../../atoms/Heading/Heading';
import { theme } from '../../../theme/theme';
import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';

interface RecentClimbData {
    id: number;
    courseId: number;
    forecastDate: string;
    updatedAt: string;
    mountainName: string;
    courseName: string;
}

export default function RecentClimbSection() {
    const { data: recentClimbData } = useApiQuery<RecentClimbData[]>(
        `/card/interaction/history`,
        { pageSize: 5 },
        {
            retry: false,
        },
    );

    const formatDate = (dateStr: string): string => {
        if (!dateStr) return '';
        return dateStr.split('T')[0].replace(/-/g, '.');
    };

    return (
        <div>
            <LabelHeading HeadingTag='h2'>최근 본 등산일정</LabelHeading>
            <div css={listStyles}>
                {recentClimbData?.map((item, index) => {
                    const {
                        id,
                        courseId,
                        forecastDate,
                        updatedAt,
                        mountainName,
                        courseName,
                    } = item;

                    return (
                        <div css={wrapperStyles} key={index}>
                            <div css={headerStyles}>
                                <CommonText
                                    TextTag='span'
                                    fontSize='caption'
                                    fontWeight='medium'
                                    color='grey-80'
                                >
                                    {formatDate(forecastDate)}
                                </CommonText>
                                <button css={buttonStyles}>
                                    <Icon
                                        name='narrow-right'
                                        width={2}
                                        height={2}
                                        color='grey-0'
                                    />
                                </button>
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

const buttonStyles = css`
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
