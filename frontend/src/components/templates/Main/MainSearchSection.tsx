import SearchBar from '../../organisms/Common/SearchBar.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import useMainSearchSection from './hooks/useMainSearchSection.ts';

interface PropsState {
    onFormValidChange: (isValid: boolean) => void;
}

export default function MainSearchSection(props: PropsState) {
    const { onFormValidChange } = props;

    const {
        selectedMountainId,
        selectedCourseId,
        selectedWeekdayId,
        mountainOptions,
        courseOptions,
        mountainChangeHandler,
        courseChangeHandler,
        weekdayChangeHandler,
        isModalOpen,
        isMountainsError,
        isFormValidate,
        navigateToForecast,
    } = useMainSearchSection();

    const submitHandler = (e: React.FormEvent) => {
        const isValid = isFormValidate(e);
        onFormValidChange(isValid);
        if (isValid) navigateToForecast();
    };

    return (
        <>
            <SearchBar
                searchBarTitle='어디 날씨를 확인해볼까요?'
                searchBarMessage='를 오르는'
                pageName='main'
                mountainOptions={mountainOptions}
                selectedMountainId={selectedMountainId ?? 0}
                mountainChangeHandler={mountainChangeHandler}
                courseOptions={courseOptions}
                selectedCourseId={selectedCourseId ?? 0}
                courseChangeHandler={courseChangeHandler}
                weekdayOptions={weekdayData}
                selectedWeekdayId={selectedWeekdayId ?? 0}
                weekdayChangeHandler={weekdayChangeHandler}
                onSubmit={submitHandler}
            />
            {isModalOpen && (
                <Modal onClose={() => window.location.reload()}>
                    {isMountainsError
                        ? '산 정보를 불러오는데 실패했습니다.'
                        : '코스 정보를 불러오는데 실패했습니다.'}
                </Modal>
            )}
        </>
    );
}

const weekdayData = [
    { id: 1, name: '오늘' },
    { id: 2, name: '내일' },
    { id: 3, name: '모레' },
];
