type DropdownType = 'mountain' | 'course' | 'weekday' | null;
export const createHandleToggleDropdown =
    ({
        setOpenDropdown,
        openDropdown,
    }: {
        setOpenDropdown: React.Dispatch<React.SetStateAction<DropdownType>>;
        openDropdown: DropdownType;
    }) =>
    (dropdown: DropdownType) => {
        setOpenDropdown(openDropdown === dropdown ? null : dropdown);
    };

interface createHandleSubmitProps {
    isMainPage: boolean;
    isReportPage: boolean;
    selectedMountainId: string;
    selectedCourseId?: string;
    selectedWeekdayId?: string;
    onSubmit: (values: {
        mountainId: string;
        courseId?: string;
        weekdayId?: string;
    }) => void;
}
export const createHandleSubmit =
    ({
        selectedMountainId,
        selectedCourseId = 'null',
        selectedWeekdayId = 'null',
        onSubmit,
    }: createHandleSubmitProps) =>
    () => {
        onSubmit({
            mountainId: selectedMountainId,
            courseId: selectedCourseId,
            weekdayId: selectedWeekdayId,
        });
    };
