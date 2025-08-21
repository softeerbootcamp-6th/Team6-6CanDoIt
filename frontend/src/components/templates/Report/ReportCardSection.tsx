import { HeadlineHeading } from '../../atoms/Heading/Heading.tsx';
import FrontReportCard from '../../organisms/Report/FrontReportCard.tsx';
import { css } from '@emotion/react';
import ChipButton from '../../molecules/Button/ChipButton.tsx';
import ToggleButton from '../../atoms/Button/ToggleButton.tsx';
import { type FormEvent, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import BackReportCard from '../../organisms/Report/BackReportCard.tsx';
import ReportModal from '../../organisms/Report/ReportModal.tsx';
import {
    formatTimeDifference,
    getCurrentTime,
    filterGatherer,
} from './utils.ts';
import useApiQuery from '../../../hooks/useApiQuery.ts';
import useApiMutation from '../../../hooks/useApiMutation.ts';
import Modal from '../../molecules/Modal/RegisterModal.tsx';

type ReportType = 'WEATHER' | 'SAFE';

interface CardData {
    reportId: number;
    reportType: string;
    createdAt: string;
    nickname: string;
    userImageUrl: string;
    imageUrl: string;
    content: string;
    likeCount?: number;
    weatherKeywords?: string[];
    rainKeywords?: string[];
    etceteraKeywords?: string[];
}
interface ReportFormData {
    courseId: number;
    type: ReportType;
    content: string;
    weatherKeywords: number[];
    rainKeywords: number[];
    etceteraKeywords: number[];
}

export default function ReportCardSection() {
    const [reportType, setReportType] = useState<ReportType>('WEATHER');
    const [flippedCardId, setFlippedCardId] = useState<number | null>(null);
    const [isReportModalOpen, setIsReportModalOpen] = useState(false);
    const [validationError, setValidationError] = useState<string>('');
    const [searchParams] = useSearchParams();
    const mountainId = searchParams.get('mountainid');
    const courseId = searchParams.get('courseid');

    const title =
        reportType === 'WEATHER' ? '실시간 날씨 제보' : '실시간 안전 제보';
    const currentTime = getCurrentTime();

    const { data: filterColumn } = useApiQuery(
        '/card/interaction/keyword',
        {},
        {
            placeholderData: filterKeywords,
            retry: false,
        },
    );
    const { data: cardsData = initCardData } = useApiQuery<CardData[]>(
        `/card/interaction/report/${courseId}`,
        { reportType },
        {
            placeholderData: initCardData,
            retry: false,
        },
    );
    const reportMutation = useApiMutation<FormData, any>(
        '/card/interaction/report',
        'POST',
        {
            onSuccess: () => {
                setIsReportModalOpen(false);
                setFlippedCardId(null);
            },
            onError: (error) => {
                console.error('Report submission failed:', error);
            },
        },
    );
    const cardLikeMutation = useApiMutation(
        `/card/interaction/report/like/${flippedCardId}`,
        'POST',
        {
            onSuccess: () => {
                console.log('Like action successful');
            },
            onError: (error) => {
                console.error('Like action failed:', error);
            },
        },
    );

    const formValidation = (formData: FormData) => {
        const imageFile = formData.get('image') as File;
        if (!imageFile || !imageFile.type.startsWith('image/')) {
            return '이미지 파일을 업로드해주세요';
        }

        const content = formData.get('content') as string;
        if (!content || content.trim() === '') {
            return '제보 내용을 입력해주세요';
        }

        const weatherKeywords = formData.getAll('weatherKeywords');
        const rainKeywords = formData.getAll('rainKeywords');
        const etceteraKeywords = formData.getAll('etceteraKeywords');
        if (
            weatherKeywords.length === 0 &&
            rainKeywords.length === 0 &&
            etceteraKeywords.length === 0
        ) {
            return '키워드를 선택해주세요';
        }

        return '';
    };

    const reportModalSubmitHandler = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const form = e.currentTarget;
        const formData = new FormData(form);

        const validationErr = formValidation(formData);
        setValidationError(validationErr);
        if (validationErr) return;

        const ids = (keyword: string) =>
            formData.getAll(keyword).map((id) => Number(id));

        const requestData: ReportFormData = {
            courseId: Number(courseId),
            type: reportType,
            content: formData.get('content') as string,
            weatherKeywords: ids('weatherKeywords'),
            rainKeywords: ids('rainKeywords'),
            etceteraKeywords: ids('etceteraKeywords'),
        };
        const imageFile = formData.get('image') as File;

        const payload = new FormData();
        payload.append(
            'request',
            new Blob([JSON.stringify(requestData)], {
                type: 'application/json',
            }),
        );
        payload.append('imageFile', imageFile);
        reportMutation.mutate(payload);
    };

    const reportCardSectionToggleButtonHandler = () => {
        setReportType((prev) => (prev === 'WEATHER' ? 'SAFE' : 'WEATHER'));
    };

    const wheelHandler = (event: React.WheelEvent<HTMLDivElement>) => {
        event.currentTarget.scrollLeft += Number(event.deltaY);
    };

    if (!mountainId || !courseId) return null;

    return (
        <>
            <div css={reportTitleStyle}>
                <HeadlineHeading HeadingTag='h2'>{title}</HeadlineHeading>
                <ToggleButton
                    isOn={reportType === 'SAFE'}
                    onClick={() => reportCardSectionToggleButtonHandler()}
                />
                <ChipButton
                    onClick={() => setIsReportModalOpen(true)}
                    text='제보하기'
                    iconName='edit-03'
                />
                <ReportModal
                    title='실시간 날씨 제보하기'
                    description='실시간 날씨를 제보하려면 코스 근처에 있어야 해요. 사진은 6시간 이내에 촬영된 사진만 업로드 가능해요.'
                    filterColumn={filterColumn ?? filterKeywords}
                    isOpen={isReportModalOpen}
                    onClose={() => setIsReportModalOpen(false)}
                    onSubmit={reportModalSubmitHandler}
                />
                {validationError && (
                    <Modal onClose={() => setValidationError('')}>
                        {validationError}
                    </Modal>
                )}
            </div>
            <div css={reportCardContainerStyle} onWheel={wheelHandler}>
                {cardsData.map((card) => {
                    const filterLabels = filterGatherer({
                        weatherKeywords: card.weatherKeywords,
                        rainKeywords: card.rainKeywords,
                        etceteraKeywords: card.etceteraKeywords,
                    });
                    const timeAgo = formatTimeDifference({
                        pastISO: card.createdAt,
                        nowDate: currentTime,
                    });
                    return flippedCardId === card.reportId ? (
                        <BackReportCard
                            key={card.reportId}
                            comment={card.content}
                            timeAgo={timeAgo}
                            likeCount={card.likeCount}
                            filterLabels={filterLabels}
                            onClick={() => setFlippedCardId(null)}
                            onHeartClick={() => cardLikeMutation.mutate({})}
                        />
                    ) : (
                        <FrontReportCard
                            key={card.reportId}
                            comment={card.content}
                            timeAgo={timeAgo}
                            filterLabels={filterLabels}
                            onClick={() => setFlippedCardId(card.reportId)}
                        />
                    );
                })}
            </div>
        </>
    );
}

const reportCardContainerStyle = css`
    display: flex;
    flex-direction: row;
    gap: 1rem;
    overflow-x: auto;
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
    margin-top: 3rem;
    margin-left: 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 1.5rem;
`;

const filterKeywords = {
    weatherKeywords: [
        { id: 0, description: '화창해요' },
        { id: 1, description: '구름이 많아요' },
        { id: 2, description: '더워요' },
        { id: 3, description: '추워요' },
    ],
    rainKeywords: [
        { id: 0, description: '부슬비가 내려요' },
        { id: 1, description: '장대비가 쏟아져요' },
        { id: 2, description: '천둥 번개가 쳐요' },
        { id: 3, description: '폭우가 내려요' },
    ],
    etceteraKeywords: [
        { id: 0, description: '안개가 껴요' },
        { id: 1, description: '미세먼지가 많아요' },
        { id: 2, description: '시야가 흐려요' },
    ],
};

const initCardData = [
    {
        reportId: 101,
        reportType: 'WEATHER',
        createdAt: '2025-08-18T09:12:00',
        nickname: '등산고수',
        userImageUrl: 'https://cdn.example.com/users/u123.png',
        imageUrl: 'https://cdn.example.com/reports/r101.jpg',
        content: '산 정상은 바람이 강해요. 주의하세요!',
        likeCount: 24,
        weatherKeywords: ['화창해요', '더워요'],
        rainKeywords: ['부슬비가 내려요'],
        etceteraKeywords: ['안개가 껴요'],
    },
    {
        reportId: 102,
        reportType: 'SAFETY',
        createdAt: '2025-08-18T10:05:00',
        nickname: '안전지킴이',
        userImageUrl: 'https://cdn.example.com/users/u456.png',
        imageUrl: 'https://cdn.example.com/reports/r102.jpg',
        content: '산길이 미끄러워요. 조심하세요!',
        likeCount: 15,
        weatherKeywords: ['구름이 많아요'],
        rainKeywords: ['장대비가 쏟아져요'],
        etceteraKeywords: ['미세먼지가 많아요'],
    },
    {
        reportId: 103,
        reportType: 'WEATHER',
        createdAt: '2025-08-18T11:30:00',
        nickname: '산악인',
        userImageUrl: 'https://cdn.example.com/users/u789.png',
        imageUrl: 'https://cdn.example.com/reports/r103.jpg',
        content: '오늘은 바람이 많이 불어요. 모자 조심하세요!',
        likeCount: 30,
        weatherKeywords: ['바람'],
        rainKeywords: [],
        etceteraKeywords: ['시야가 흐려요'],
    },
    {
        reportId: 104,
        reportType: 'SAFETY',
        createdAt: '2025-08-18T12:45:00',
        nickname: '등산러버',
        userImageUrl: 'https://cdn.example.com/users/u321.png',
        imageUrl: 'https://cdn.example.com/reports/r104.jpg',
        content: '산길에 돌이 많아요. 조심하세요!',
        likeCount: 10,
        weatherKeywords: ['추워요'],
        rainKeywords: [],
        etceteraKeywords: [],
    },
    {
        reportId: 105,
        reportType: 'WEATHER',
        createdAt: '2025-08-18T13:20:00',
        nickname: '자연사랑',
        userImageUrl: 'https://cdn.example.com/users/u654.png',
        imageUrl: 'https://cdn.example.com/reports/r105.jpg',
        content: '오늘은 정말 맑아요! 경치가 최고예요.',
        likeCount: 50,
        weatherKeywords: ['화창해요'],
        rainKeywords: [],
        etceteraKeywords: [],
    },
    {
        reportId: 106,
        reportType: 'SAFETY',
        createdAt: '2025-08-18T14:10:00',
        nickname: '안전맨',
        userImageUrl: 'https://cdn.example.com/users/u987.png',
        imageUrl: 'https://cdn.example.com/reports/r106.jpg',
        content: '산길에 낙석이 있어요. 조심하세요!',
        likeCount: 20,
        weatherKeywords: [],
        rainKeywords: ['천둥 번개가 쳐요'],
        etceteraKeywords: ['안개가 껴요'],
    },
    {
        reportId: 107,
        reportType: 'WEATHER',
        createdAt: '2025-08-18T15:00:00',
        nickname: '등산왕',
        userImageUrl: 'https://cdn.example.com/users/u111.png',
        imageUrl: 'https://cdn.example.com/reports/r107.jpg',
        content: '오늘은 정말 더워요. 충분한 수분 섭취하세요!',
        likeCount: 40,
        weatherKeywords: ['더워요'],
        rainKeywords: [],
        etceteraKeywords: [],
    },
    {
        reportId: 108,
        reportType: 'SAFETY',
        createdAt: '2025-08-18T16:30:00',
        nickname: '안전지킴이',
        userImageUrl: 'https://cdn.example.com/users/u222.png',
        imageUrl: 'https://cdn.example.com/reports/r108.jpg',
        content: '산길에 미끄러운 곳이 있어요. 조심하세요!',
        likeCount: 18,
        weatherKeywords: [],
        rainKeywords: ['폭우가 내려요'],
        etceteraKeywords: ['시야가 흐려요'],
    },
    {
        reportId: 109,
        reportType: 'WEATHER',
        createdAt: '2025-08-18T17:15:00',
        nickname: '자연사랑',
        userImageUrl: 'https://cdn.example.com/users/u333.png',
        imageUrl: 'https://cdn.example.com/reports/r109.jpg',
        content: '오늘은 바람이 정말 시원해요. 기분이 좋아요!',
        likeCount: 35,
        weatherKeywords: ['바람'],
        rainKeywords: [],
        etceteraKeywords: [],
    },
];
