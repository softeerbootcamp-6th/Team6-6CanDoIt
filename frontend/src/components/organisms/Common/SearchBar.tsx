import Dropdown from '../../molecules/Dropdown/Dropdown.tsx';
import { LabelHeading } from '../../atoms/Heading/Heading.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';
import SearchBarText from '../../atoms/Text/SearchBarText.tsx';
import { useState } from 'react';
import {
    createHandleSubmit,
    createHandleToggleDropdown,
} from './utils/utils.ts';

type PageName = 'main' | 'report' | 'safety';
type DropdownType = 'mountain' | 'course' | 'weekday' | null;

interface Option {
    id: string;
    name: string;
}

interface PropsState {
    searchBarTitle?: string;
    searchBarMessage: string;
    pageName?: PageName;
    mountainOptions: Option[];
    courseOptions?: Option[];
    weekdayOptions?: Option[];
    onSubmit: (values: {
        mountainId: string;
        courseId?: string;
        weekdayId?: string;
    }) => void;
    onMountainChange?: (mountain: Option) => void;
}

export default function SearchBar(props: PropsState) {
    const {
        searchBarTitle,
        searchBarMessage,
        pageName = 'main',
        mountainOptions,
        courseOptions = [],
        weekdayOptions = [],
        onSubmit,
        onMountainChange,
    } = props;

    const [openedDropdownType, setOpenedDropdownType] =
        useState<DropdownType>(null);
    const [selectedMountain, setSelectedMountain] =
        useState<Option>(defaultMountain);
    const [selectedCourse, setSelectedCourse] = useState<Option>(defaultCourse);
    const [selectedWeekday, setSelectedWeekday] =
        useState<Option>(defaultWeekday);

    const isMainPage = pageName === 'main';
    const isReportPage = pageName === 'report';

    const handleToggleDropdown = createHandleToggleDropdown({
        setOpenDropdown: setOpenedDropdownType,
        openDropdown: openedDropdownType,
    });
    const handleSubmit = createHandleSubmit({
        isMainPage,
        isReportPage,
        selectedMountainId: selectedMountain.id,
        selectedCourseId: selectedCourse.id,
        selectedWeekdayId: selectedWeekday.id,
        onSubmit,
    });
    const onSearchSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        handleSubmit();
    };

    const dropdownProps = {
        mountain: createDropdownProps({
            title: '산',
            options: mountainOptions,
            dropdownType: 'mountain',
            openedDropdownType: openedDropdownType,
            handleToggleDropdown,
            onSelectValue: (selected: Option) => {
                setSelectedMountain(selected);
                onMountainChange?.(selected);
            },
        }),
        course: createDropdownProps({
            title: '코스',
            options: courseOptions,
            dropdownType: 'course',
            openedDropdownType: openedDropdownType,
            handleToggleDropdown,
            onSelectValue: setSelectedCourse,
        }),
        weekday: createDropdownProps({
            title: '요일은?',
            options: weekdayOptions,
            dropdownType: 'weekday',
            openedDropdownType: openedDropdownType,
            handleToggleDropdown,
            onSelectValue: setSelectedWeekday,
        }),
    };

    return (
        <div css={searchBarContainerStyle}>
            <LabelHeading HeadingTag='h2'>{searchBarTitle}</LabelHeading>
            <form css={searchBarStyle} onSubmit={onSearchSubmit}>
                <Dropdown {...dropdownProps.mountain} />
                {(isMainPage || isReportPage) && (
                    <Dropdown {...dropdownProps.course} />
                )}
                <SearchBarText>{searchBarMessage}</SearchBarText>
                {isMainPage && <Dropdown {...dropdownProps.weekday} />}
                <button type='submit' css={searchButtonStyle}>
                    <Icon {...searchButtonIconProps} />
                </button>
            </form>
        </div>
    );
}

const createDropdownProps = ({
    title,
    options,
    dropdownType,
    openedDropdownType,
    handleToggleDropdown,
    onSelectValue,
}: {
    title: string;
    options: Option[];
    dropdownType: DropdownType;
    openedDropdownType: DropdownType;
    handleToggleDropdown: (dropdown: DropdownType) => void;
    onSelectValue: (selected: Option) => void;
}) => ({
    title,
    options,
    isOpenOptions: openedDropdownType === dropdownType,
    onToggle: () => handleToggleDropdown(dropdownType),
    onSelect: onSelectValue,
});

const searchButtonIconProps = {
    name: 'search-sm',
    width: 1.875,
    height: 1.875,
    color: 'grey-100',
};

const { colors } = theme;

const searchBarContainerStyle = css`
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1.5rem;
`;

const searchBarStyle = css`
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: ${colors.grey[100]};
    border-radius: 6.25rem;
    background-color: ${colors.greyOpacityWhite[70]};
    width: max-content;
    padding: 0.75rem;
`;

const searchButtonStyle = css`
    width: 3rem;
    height: 3rem;
    background-color: ${colors.greyOpacityWhite[70]};
    margin-left: auto;
    border-radius: 50%;
    border: none;
    cursor: pointer;
`;

const defaultMountain: Option = { id: '0', name: '산' };
const defaultCourse: Option = { id: '0', name: '코스' };
const defaultWeekday: Option = { id: '0', name: '요일은?' };
