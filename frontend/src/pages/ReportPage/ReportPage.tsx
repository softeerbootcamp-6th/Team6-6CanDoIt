import { theme } from '../../theme/theme.ts';
import ReportSearchSection from '../../components/templates/Report/ReportSearchSection.tsx';
import ReportCardSection from '../../components/templates/Report/ReportCardSection.tsx';

export default function ReportPage() {
    return (
        <>
            <ReportSearchSection />
            <ReportCardSection {...reportSectionWeatherProps} />
            <ReportCardSection {...reportSectionSafetyProps} />
        </>
    );
}

const cardData = [
    {
        comment:
            '사진 한 장 제대로 못 찍고 그냥 허탕이여 허탕. 에휴, 이럴 줄 알았음 진작 내려왔어야 혔는디 괜히 버텼다가 고생만 실컷 했지 뭐여~ ^^',
        minutesAgo: 10,
        filterLabels: ['맑음', '바람', '안개'],
    },
    {
        comment:
            '오늘은 날씨가 너무 좋아서 기분이 좋네요! 산에 오르는 길이 정말 아름다워요.',
        minutesAgo: 5,
        filterLabels: ['맑음', '바람'],
    },
    {
        comment: '비가 오고 있어요. 조심하세요!',
        minutesAgo: 15,
        filterLabels: ['비', '안개'],
    },
];

const reportSectionWeatherProps = {
    title: '실시간 날씨 제보',
    circleColor: theme.colors.status.normal.bad,
    cards: cardData,
};

const reportSectionSafetyProps = {
    title: '실시간 안전 제보',
    circleColor: theme.colors.status.normal.good,
    cards: cardData,
};
