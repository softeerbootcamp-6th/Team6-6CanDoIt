import { css } from '@emotion/react';
import FrontWeatherCardHeader from '../../molecules/Forecast/FrontWeatherCardHaeder';
import FrontWeatherCardFooter from '../../molecules/Forecast/FrontWeatherCardFooter';

interface PropsState {
    onClose: () => void;
    cardData: CardData;
}

interface CardData {
    mountainId: number;
    mountainName: string;
    mountainImageUrl: string;
    courseId: number;
    courseName: string;
    courseImageUrl: string;
    distance: number;
    duration: number;
    sunrise: string;
    sunset: string;
}

export default function FrontWeatherSummaryCard({
    onClose,
    cardData,
}: PropsState) {
    const {
        mountainName,
        courseName,
        courseImageUrl,
        distance,
        duration,
        sunrise,
        sunset,
    } = cardData;

    const headerData = [
        {
            iconName: 'sunrise',
            text: '일출',
            time: sunrise,
        },
        {
            iconName: 'sunset',
            text: '일몰',
            time: sunset,
        },
    ];

    return (
        <>
            <FrontWeatherCardHeader onClose={onClose} headerData={headerData} />
            <div css={dummyStyles}>
                <img src={courseImageUrl} alt={courseName} />
            </div>
            ;
            <FrontWeatherCardFooter
                data={{ mountainName, distance, courseName, duration }}
            />
        </>
    );
}

const dummyStyles = css`
    width: 100%;
    height: 60%;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
        width: 100%;
        height: 100%;
        object-fit: contain;
    }
`;
