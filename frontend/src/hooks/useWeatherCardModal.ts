import { useState } from 'react';

export default function useWeatherCardModal() {
    const [isCardOpen, setIsCardOpen] = useState(false);
    const [selectedCourseId, setSelectedCourseId] = useState<number | null>(
        null,
    );
    const [selectedForecastDate, setSelectedForecastDate] = useState<
        string | null
    >(null);

    const openCard = (courseId: number, forecastDate: string) => {
        setSelectedCourseId(courseId);
        setSelectedForecastDate(forecastDate);
        setIsCardOpen(true);
    };

    const closeCard = () => setIsCardOpen(false);

    return {
        isCardOpen,
        selectedCourseId,
        selectedForecastDate,
        openCard,
        closeCard,
    };
}
