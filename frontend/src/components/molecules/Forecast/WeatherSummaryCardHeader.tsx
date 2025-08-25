import { HeadlineHeading } from '../../atoms/Heading/Heading';
import CommonText from '../../atoms/Text/CommonText';

export default function WeatherSummaryCardHeader() {
    return (
        <>
            <HeadlineHeading HeadingTag='h2'>
                날씨 카드 생성 완료 !
            </HeadlineHeading>
            <CommonText
                TextTag='span'
                fontSize='body'
                fontWeight='medium'
                color='greyOpacityWhite-50'
            >
                저장 버튼을 통해 최근 본 등산목록에 추가하세요!
            </CommonText>
        </>
    );
}
