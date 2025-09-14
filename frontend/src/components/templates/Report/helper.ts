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
