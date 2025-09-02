import Dropdown from '../../molecules/Dropdown/Dropdown.tsx';
import { LabelHeading } from '../../atoms/Heading/Heading.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';
import SearchBarText from '../../atoms/Text/SearchBarText.tsx';
import { useState } from 'react';

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
    selectedMountainId?: number;
    mountainChangeHandler?: (id: number) => void;
    courseOptions?: Option[];
    selectedCourseId?: number;
    courseChangeHandler?: (id: number) => void;
    weekdayOptions?: Option[];
    selectedWeekdayId?: number;
    weekdayChangeHandler?: (id: number) => void;
    onSubmit: (e: React.FormEvent) => void;
}

export default function SearchBar(props: PropsState) {
    const {
        searchBarTitle,
        searchBarMessage,
        pageName = 'main',
        mountainOptions = [],
        selectedMountainId = 0,
        mountainChangeHandler = () => {},
        courseOptions = [],
        selectedCourseId = 0,
        courseChangeHandler = () => {},
        weekdayOptions = [],
        selectedWeekdayId = 0,
        weekdayChangeHandler = () => {},
        onSubmit,
    } = props;

    const [openedDropdownType, setOpenedDropdownType] =
        useState<DropdownType>(null);

    const isMainPage = pageName === 'main';
    const isReportPage = pageName === 'report';

    const toggleDropdownHandler = (dropdown: DropdownType) => {
        setOpenedDropdownType(
            openedDropdownType === dropdown ? null : dropdown,
        );
    };

    const dropdownProps = {
        mountain: createDropdownProps({
            title: '산',
            initSelectorId: selectedMountainId,
            options: mountainOptions,
            dropdownType: 'mountain',
            openedDropdownType: openedDropdownType,
            toggleDropdownHandler,
            onSelectValue: mountainChangeHandler,
        }),
        course: createDropdownProps({
            title: '코스',
            initSelectorId: selectedCourseId,
            options: courseOptions,
            dropdownType: 'course',
            openedDropdownType: openedDropdownType,
            toggleDropdownHandler,
            onSelectValue: courseChangeHandler,
        }),
        weekday: createDropdownProps({
            title: '날',
            initSelectorId: selectedWeekdayId,
            options: weekdayOptions,
            dropdownType: 'weekday',
            openedDropdownType: openedDropdownType,
            toggleDropdownHandler,
            onSelectValue: weekdayChangeHandler,
        }),
    };

    return (
        <div css={searchBarContainerStyle}>
            <LabelHeading HeadingTag='h2'>{searchBarTitle}</LabelHeading>
            <form css={searchBarStyle} onSubmit={(e) => onSubmit(e)}>
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
    initSelectorId,
    options,
    dropdownType,
    openedDropdownType,
    toggleDropdownHandler,
    onSelectValue,
}: {
    title: string;
    initSelectorId?: number;
    options: Option[];
    dropdownType: DropdownType;
    openedDropdownType: DropdownType;
    toggleDropdownHandler: (dropdown: DropdownType) => void;
    onSelectValue: (id: number) => void;
}) => {
    const initSelector = options.find((option) => option.id === initSelectorId);
    return {
        title,
        initSelector: initSelector ?? { id: 0, name: title },
        options,
        isOpenOptions: openedDropdownType === dropdownType,
        paramName: `${dropdownType}id`,
        onToggle: () => toggleDropdownHandler(dropdownType),
        onSelect: onSelectValue,
    };
};

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
