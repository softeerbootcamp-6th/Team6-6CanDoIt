import SearchBar from '../../organisms/Common/SearchBar';
import Loading from '../../organisms/Loading/Loading.tsx';
import Modal from '../../molecules/Modal/RegisterModal.tsx';
import useForecastSearchSection from './hooks/useForcastSearchSection.ts';

export default function ForecastSearchSection() {
    const {
        selectedMountainId,
        selectedCourseId,
        selectedWeekdayId,
        mountainOptions,
        courseOptions,
        mouuntainChangeHandler,
        courseChangeHandler,
        weekdayChangeHandler,
        isLoading,
        mountainTitle,
        mountainDescription,
        errorMessage,
        setErrorMessage,
        submitHandler,
        weekdayData,
    } = useForecastSearchSection();

    return (
        <>
            <SearchBar
                searchBarMessage='를 오르는'
                pageName='main'
                mountainOptions={mountainOptions}
                selectedMountainId={selectedMountainId ?? 0}
                mountainChangeHandler={mouuntainChangeHandler}
                courseOptions={courseOptions}
                selectedCourseId={selectedCourseId ?? 0}
                courseChangeHandler={courseChangeHandler}
                weekdayOptions={weekdayData}
                selectedWeekdayId={selectedWeekdayId ?? 0}
                weekdayChangeHandler={weekdayChangeHandler}
                onSubmit={submitHandler}
            />
            {isLoading && (
                <Loading
                    mountainTitle={mountainTitle}
                    mountainDescription={mountainDescription}
                />
            )}
            {errorMessage && (
                <Modal
                    onClose={() => {
                        setErrorMessage('');
                    }}
                >
                    {errorMessage}
                </Modal>
            )}
        </>
    );
}
