export function keywordToTitle(keyword: string): string {
    switch (keyword) {
        case 'weatherKeywords':
            return '날씨';
        case 'rainKeywords':
            return '비';
        default:
            return '기타';
    }
}
