type DropdownType = 'mountain' | 'course' | 'weekday' | null;
interface Option {
    id: string;
    name: string;
}
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

export const createHandleSubmit =
    ({
        isMainPage,
        isReportPage,
        selectedMountain,
        selectedCourse,
        selectedWeekday,
        onSubmit,
    }: {
        isMainPage: boolean;
        isReportPage: boolean;
        selectedMountain: Option;
        selectedCourse: Option;
        selectedWeekday: Option;
        onSubmit: (values: {
            mountain: Option;
            course?: Option;
            weekday?: Option;
        }) => void;
    }) =>
    (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit({
            mountain: selectedMountain,
            course: isMainPage || isReportPage ? selectedCourse : undefined,
            weekday: isMainPage ? selectedWeekday : undefined,
        });
    };
