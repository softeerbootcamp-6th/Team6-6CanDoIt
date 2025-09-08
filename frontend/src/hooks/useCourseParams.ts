import { useSearchParams } from 'react-router-dom';

export default function useCourseParams() {
    const [searchParams] = useSearchParams();

    return {
        selectedCourseId: Number(searchParams.get('courseid')),
        selectedMountainId: Number(searchParams.get('mountainid')),
        selectedWeekdayId: Number(searchParams.get('weekdayid')),
    };
}
