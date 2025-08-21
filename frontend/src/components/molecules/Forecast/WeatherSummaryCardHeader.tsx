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
                이미지를 저장 또는 공유하여 등산시 참고해주세요
            </CommonText>
        </>
    );
}
