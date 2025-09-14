import { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useQueryClient } from '@tanstack/react-query';

import useCardLikeMutation from '../../../../hooks/useCardLikeMutation';

import {
    getCurrentTime,
    parseFilterFromUrl,
    validateAccessToken,
} from '../../../../utils/utils';

import { reportFormValidation } from '../helper.ts';

import useFilterColumn from '../../../../hooks/useFilterColumn.ts';
import useReportCardData from '../../../../hooks/useReportCard.ts';
import useReportMutation from '../../../../hooks/useReportMutation.ts';

type ReportType = 'WEATHER' | 'SAFE';

interface ReportFormData {
    courseId: number;
    type: ReportType;
    content: string;
    weatherKeywords: number[];
    rainKeywords: number[];
    etceteraKeywords: number[];
}

export default function useReportCardSection() {
    const [reportType, setReportType] = useState<ReportType>('WEATHER');
    const [flippedCardId, setFlippedCardId] = useState<number | null>(null);
    const [isReportModalOpen, setIsReportModalOpen] = useState(false);
    const [validationError, setValidationError] = useState<string>('');

    const [searchParams] = useSearchParams();
    const queryClient = useQueryClient();
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));
    const pageSize = 10;

    const weatherKeywords = parseFilterFromUrl(searchParams, 'weatherKeywords');
    const rainKeywords = parseFilterFromUrl(searchParams, 'rainKeywords');
    const etceteraKeywords = parseFilterFromUrl(
        searchParams,
        'etceteraKeywords',
    );

    const title =
        reportType === 'WEATHER' ? '실시간 날씨 제보' : '실시간 안전 제보';
    const currentTime = getCurrentTime();

    const { data: filterColumn, isError: isFilterColumnError } =
        useFilterColumn();

    const {
        data: cardsData,
        fetchNextPage,
        hasNextPage,
        isFetchingNextPage,
        isError: isCardsError,
    } = useReportCardData({
        courseId,
        reportType,
        weatherKeywords,
        rainKeywords,
        etceteraKeywords,
        pageSize,
    });

    useEffect(() => {
        const errorMessage = isFilterColumnError ? '키워드' : '제보 카드';
        if (isFilterColumnError || isCardsError) {
            setValidationError(`${errorMessage} 불러오기에 실패했습니다.`);
        }
    }, [isFilterColumnError, isCardsError]);

    const flattenedData = cardsData?.pages.flat();

    const invalidQueryKey = [
        `/card/interaction/report/${courseId}`,
        {
            reportType,
            weatherKeywordIds: weatherKeywords,
            rainKeywordIds: rainKeywords,
            etceteraKeywordIds: etceteraKeywords,
            pageSize,
        },
    ];
    const reportSuccessCallback = async () => {
        await queryClient.invalidateQueries({ queryKey: invalidQueryKey });
        setIsReportModalOpen(false);
        setFlippedCardId(null);
    };
    const reportErrorCallback = (error: any) => {
        setIsReportModalOpen(false);
        error instanceof TypeError
            ? setValidationError('네트워크 오류가 발생했습니다.')
            : setValidationError('제보에 실패했습니다.');
    };
    const reportMutation = useReportMutation({
        onSuccess: reportSuccessCallback,
        onError: reportErrorCallback,
    });

    const { heartClickHandler, validationError: likeValidationError } =
        useCardLikeMutation({
            reportId: flippedCardId,
            queryKey: invalidQueryKey,
        });

    useEffect(() => {
        if (likeValidationError) {
            setValidationError(likeValidationError);
        }
    }, [likeValidationError]);

    const reportModalSubmitHandler = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const form = e.currentTarget;
        const formData = new FormData(form);

        const validationErr = reportFormValidation(formData);
        setValidationError(validationErr);
        if (validationErr) return;

        if (!validateAccessToken()) {
            setValidationError('로그인이 필요합니다.');
            return;
        }

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

    const chipButtonClickHandler = () => {
        if (!validateAccessToken()) {
            setValidationError('로그인이 필요합니다.');
            return;
        }
        setIsReportModalOpen(true);
    };

    return {
        mountainId,
        courseId,
        title,
        currentTime,

        reportType,
        setReportType,
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
    };
}
