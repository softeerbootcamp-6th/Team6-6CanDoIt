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
