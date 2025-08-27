export interface CardData {
    reportId: number;
    reportType: string;
    createdAt: string;
    nickname: string;
    userImageUrl: string;
    imageUrl: string;
    content: string;
    likeCount?: number;
    isLiked?: boolean;
    weatherKeywords?: string[];
    rainKeywords?: string[];
    etceteraKeywords?: string[];
}
