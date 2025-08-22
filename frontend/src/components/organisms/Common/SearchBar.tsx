import Dropdown from '../../molecules/Dropdown/Dropdown.tsx';
import { LabelHeading } from '../../atoms/Heading/Heading.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';
import SearchBarText from '../../atoms/Text/SearchBarText.tsx';
import { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';

type PageName = 'main' | 'report' | 'safety';
type DropdownType = 'mountain' | 'course' | 'weekday' | null;

interface Option {
    id: number;
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
        mountainId: number;
        courseId?: number;
        weekdayId?: number;
    }) => void;
}

export default function SearchBar(props: PropsState) {
    const {
        searchBarTitle,
        searchBarMessage,
        pageName = 'main',
        mountainOptions = [],
        courseOptions = [],
        weekdayOptions = [],
        onSubmit,
    } = props;

    const [openedDropdownType, setOpenedDropdownType] =
        useState<DropdownType>(null);
    const [selectedMountain, setSelectedMountain] =
        useState<Option>(defaultMountain);
    const [selectedCourse, setSelectedCourse] = useState<Option>(defaultCourse);
    const [selectedWeekday, setSelectedWeekday] =
        useState<Option>(defaultWeekday);

    const [searchParams, setSearchParams] = useSearchParams();
    const mountainId = Number(searchParams.get('mountainid'));
    const courseId = Number(searchParams.get('courseid'));
    const weekdayId = Number(searchParams.get('weekdayid'));

    useEffect(() => {
        const mountain =
            mountainOptions.find((option) => option.id === mountainId) ||
            defaultMountain;
        const course =
            courseOptions.find((option) => option.id === courseId) ||
            defaultCourse;
        const weekday =
            weekdayOptions.find((option) => option.id === weekdayId) ||
            defaultWeekday;

        setSelectedMountain(mountain);
        setSelectedCourse(course);
        setSelectedWeekday(weekday);
    }, [
        mountainId,
        courseId,
        weekdayId,
        mountainOptions,
        courseOptions,
        weekdayOptions,
    ]);

    const isMainPage = pageName === 'main';
    const isReportPage = pageName === 'report';

    const toggleDropdownHandler = (dropdown: DropdownType) => {
        setOpenedDropdownType(
            openedDropdownType === dropdown ? null : dropdown,
        );
    };
    const mountainChangeHandler = (mountain: Option) => {
        const next = new URLSearchParams(searchParams);
        next.set('mountainid', String(mountain.id));
        next.delete('courseid');
        setSearchParams(next);
    };
    const courseChangeHandler = (course: Option) => {
        const next = new URLSearchParams(searchParams);
        next.set('courseid', String(course.id));
        setSearchParams(next);
    };
    const weekdayChangeHandler = (weekday: Option) => {
        const next = new URLSearchParams(searchParams);
        next.set('weekdayid', String(weekday.id));
        setSearchParams(next);
    };
    const submitHandler = () => {
        console.log(
            `mountainId: ${selectedMountain.id}, courseId: ${selectedCourse.id}, weekdayId: ${selectedWeekday.id}`,
        );
        onSubmit({
            mountainId: selectedMountain.id,
            courseId: selectedCourse.id,
            weekdayId: selectedWeekday.id,
        });
    };
    const onSearchSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        submitHandler();
    };

    const dropdownProps = {
        mountain: createDropdownProps({
            title: '산',
            options: mountainOptions,
            dropdownType: 'mountain',
            openedDropdownType: openedDropdownType,
            toggleDropdownHandler,
            onSelectValue: (selected: Option) => {
                setSelectedMountain(selected);
                mountainChangeHandler(selected);
            },
        }),
        course: createDropdownProps({
            title: '코스',
            options: courseOptions,
            dropdownType: 'course',
            openedDropdownType: openedDropdownType,
            toggleDropdownHandler,
            onSelectValue: (selected: Option) => {
                setSelectedCourse(selected);
                courseChangeHandler(selected);
            },
        }),
        weekday: createDropdownProps({
            title: '요일은?',
            options: weekdayOptions,
            dropdownType: 'weekday',
            openedDropdownType: openedDropdownType,
            toggleDropdownHandler,
            onSelectValue: (selected: Option) => {
                setSelectedWeekday(selected);
                weekdayChangeHandler(selected);
            },
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
    toggleDropdownHandler,
    onSelectValue,
}: {
    title: string;
    options: Option[];
    dropdownType: DropdownType;
    openedDropdownType: DropdownType;
    toggleDropdownHandler: (dropdown: DropdownType) => void;
    onSelectValue: (selected: Option) => void;
}) => ({
    title,
    options,
    isOpenOptions: openedDropdownType === dropdownType,
    paramName: `${dropdownType}id`,
    onToggle: () => toggleDropdownHandler(dropdownType),
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

const defaultMountain: Option = { id: 0, name: '산' };
const defaultCourse: Option = { id: 0, name: '코스' };
const defaultWeekday: Option = { id: 0, name: '요일은?' };
