import { HeadlineHeading } from '../../atoms/Heading/Heading.tsx';
import FrontReportCard from '../../organisms/Report/FrontReportCard.tsx';
import { css } from '@emotion/react';
import ChipButton from '../../molecules/Button/ChipButton.tsx';
import ToggleButton from '../../atoms/Button/ToggleButton.tsx';
import Icon from '../../atoms/Icon/Icons.tsx';
import BackReportCard from '../../organisms/Report/BackReportCard.tsx';
import ReportModal from '../../organisms/Report/ReportModal.tsx';
import { formatTimeDifference, filterGatherer } from './utils.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import { theme } from '../../../theme/theme.ts';
import ReportPendingModal from '../../molecules/Modal/ReportPendingModal.tsx';
import LoginRequiredModal from '../../molecules/Modal/LoginRequiredModal.tsx';
import ReportCardWrapper from '../../organisms/Report/ReportCardWrapper.tsx';
import useReportCardSection from './hooks/useReportCardSection.ts';

export default function ReportCardSection() {
    const {
        mountainId,
        courseId,
        title,
        currentTime,

        reportType,
        flippedCardId,
        setFlippedCardId,
        isReportModalOpen,
        setIsReportModalOpen,
        validationError,
        setValidationError,

        filterColumn,
        flattenedData,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,

        reportMutation,
        heartClickHandler,
        reportModalSubmitHandler,
        reportCardSectionToggleButtonHandler,
        chipButtonClickHandler,
    } = useReportCardSection();

    const wheelHandler = (event: React.WheelEvent<HTMLDivElement>) => {
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };

    if (!mountainId || !courseId || mountainId === 0 || courseId === 0)
        return null;

    const renderReportCards = () => {
        if (!flattenedData || flattenedData.length === 0) {
            return (
                <ReportCardWrapper
                    isEmptyCard={true}
                    onClick={() => setIsReportModalOpen(true)}
                >
                    <span css={goToReportStyle}>첫 제보 남기기</span>
                </ReportCardWrapper>
            );
        }
        return flattenedData?.map((card) => {
            const isFlipped = flippedCardId === card.reportId;
            const filterLabels = filterGatherer({
                weatherKeywords: card.weatherKeywords,
                rainKeywords: card.rainKeywords,
                etceteraKeywords: card.etceteraKeywords,
            });
            const timeAgo = formatTimeDifference({
                pastISO: card.createdAt,
                nowDate: currentTime,
            });

            return (
                <div
                    key={card.reportId}
                    css={[card3DStyle, isFlipped && card3DFlippedStyle]}
                >
                    <div css={cardFaceFrontStyle}>
                        <FrontReportCard
                            comment={card.content}
                            timeAgo={timeAgo}
                            filterLabels={filterLabels}
                            onClick={() => setFlippedCardId(card.reportId)}
                            imgSrc={card.imageUrl}
                            userImageUrl={card.userImageUrl}
                        />
                    </div>
                    <div css={cardFaceBackStyle}>
                        <BackReportCard
                            comment={card.content}
                            timeAgo={timeAgo}
                            userImageUrl={card.userImageUrl}
                            likeCount={card.likeCount}
                            filterLabels={filterLabels}
                            onClick={() => setFlippedCardId(null)}
                            isLiked={card.isLiked}
                            onHeartClick={heartClickHandler}
                        />
                    </div>
                </div>
            );
        });
    };

    const renderModals = () => (
        <>
            <ReportModal
                title='실시간 제보하기'
                description='사진, 내용, 키워드를 선택하여 제보해주세요.'
                filterColumn={filterColumn ?? []}
                isOpen={isReportModalOpen}
                onClose={() => setIsReportModalOpen(false)}
                onSubmit={reportModalSubmitHandler}
            />
            {validationError === '로그인이 필요합니다.' ? (
                <LoginRequiredModal onClose={() => setValidationError('')} />
            ) : (
                validationError && (
                    <Modal onClose={() => setValidationError('')}>
                        {validationError}
                    </Modal>
                )
            )}
            {reportMutation.isPending && <ReportPendingModal />}
        </>
    );

    return (
        <>
            <div css={reportTitleStyle}>
                <HeadlineHeading HeadingTag='h2'>{title}</HeadlineHeading>
                <ToggleButton
                    isOn={reportType === 'SAFE'}
                    onClick={reportCardSectionToggleButtonHandler}
                    offBgColor={colors.grey[30]}
                    onBgColor={colors.grey[30]}
                    onCircleColor={colors.status.normal.good}
                    offCircleColor={colors.status.normal.bad}
                />
                <ChipButton
                    onClick={chipButtonClickHandler}
                    text='제보하기'
                    iconName='edit-03'
                />
                {renderModals()}
            </div>
            <div css={reportCardContainerStyle} onWheel={wheelHandler}>
                {renderReportCards()}
                <div css={loadMoreContainerStyle}>
                    {hasNextPage && (
                        <button
                            css={loadMoreButtonStyle(isFetchingNextPage)}
                            onClick={() => fetchNextPage()}
                            disabled={isFetchingNextPage}
                        >
                            <Icon {...loadMoreIconProps} />
                        </button>
                    )}
                </div>
            </div>
        </>
    );
}

const loadMoreIconProps = {
    name: 'chevron-down',
    width: 2,
    height: 2,
    color: 'grey-100',
};

const { colors } = theme;

const goToReportStyle = css`
    font-size: 2rem;
    color: ${colors.grey[100]};
    margin: auto;
`;

const loadMoreContainerStyle = css`
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 1rem;
`;

const loadMoreButtonStyle = (isLoading: boolean) => css`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 3rem;
    height: 3rem;
    background: none;
    border: none;
    cursor: ${isLoading ? 'default' : 'pointer'};
    opacity: ${isLoading ? 0.5 : 1};
    transform: rotate(-90deg) scale(1.3);
    transition:
        opacity 200ms ease,
        transform 200ms ease;

    &:hover {
        transform: ${isLoading ? 'none' : 'scale(1.5) rotate(-90deg)'};
    }

    &:disabled {
        opacity: 0.5;
        cursor: default;
    }
`;

const card3DStyle = css`
    display: grid;
    grid-auto-rows: 1fr;
    transform-style: preserve-3d;
    transition: transform 0.6s ease;
    will-change: transform;
    width: max-content;
`;

const card3DFlippedStyle = css`
    transform: rotateY(180deg);
`;

const faceBase = css`
    grid-area: 1 / 1;
    backface-visibility: hidden;
`;

const cardFaceFrontStyle = css`
    ${faceBase};
`;

const cardFaceBackStyle = css`
    ${faceBase};
    transform: rotateY(180deg);
`;

const reportCardContainerStyle = css`
    display: flex;
    flex-direction: row;
    gap: 1rem;
    overflow-x: auto;
    overflow-y: hidden;
    margin-left: 2rem;
    margin-top: 2rem;

    scrollbar-width: none;
    -ms-overflow-style: none;

    &::-webkit-scrollbar {
        display: none;
    }
`;

const reportTitleStyle = css`
    width: max-content;
    margin-top: 1rem;
    margin-left: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 1.5rem;
`;
