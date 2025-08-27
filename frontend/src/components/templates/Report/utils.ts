export function getCurrentTime(): Date {
    return new Date();
}

export function formatTimeDifference({
    pastISO,
    nowDate = null,
}: {
    pastISO: string;
    nowDate?: Date | null;
}): string {
    const now = nowDate ?? getCurrentTime();
    const past = new Date(pastISO);
    const diffMs = now.getTime() - past.getTime();

    if (diffMs < 0) return '';

    const diffSec = Math.floor(diffMs / 1000);
    const diffMin = Math.floor(diffSec / 60);
    const diffHour = Math.floor(diffMin / 60);
    const diffDay = Math.floor(diffHour / 24);

    if (diffMin < 60) {
        return `${diffMin}분 전`;
    } else if (diffHour < 24) {
        return `${diffHour}시간 전`;
    } else {
        return `${diffDay}일 전`;
    }
}

export function filterGatherer({
    weatherKeywords = [],
    rainKeywords = [],
    etceteraKeywords = [],
}: {
    weatherKeywords?: string[];
    rainKeywords?: string[];
    etceteraKeywords?: string[];
}) {
    return [...weatherKeywords, ...rainKeywords, ...etceteraKeywords];
}

export const reportFormValidation = (formData: FormData) => {
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
