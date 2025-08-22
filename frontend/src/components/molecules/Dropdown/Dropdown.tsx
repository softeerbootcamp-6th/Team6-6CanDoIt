import SearchBarText from '../../atoms/Text/SearchBarText.tsx';
import TextLi from '../../atoms/Li/TextLi.tsx';
import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';
import { useState, useEffect, useRef } from 'react';
import { useSearchParams } from 'react-router-dom';

interface Option {
    id: number;
    name: string;
}

interface PropsState {
    title: string;
    options: Option[];
    isOpenOptions?: boolean;
    paramName?: string;
    onToggle: () => void;
    onSelect?: (option: Option) => void;
}

export default function Dropdown(props: PropsState) {
    const {
        title,
        options,
        isOpenOptions = false,
        paramName,
        onToggle,
        onSelect,
    } = props;

    const [selector, setSelector] = useState({ id: 0, name: title });
    const [searchParams] = useSearchParams();
    const outsideRef = useRef<HTMLDivElement | null>(null);

    const outsideClickHandler = (event: MouseEvent) => {
        if (
            isOpenOptions &&
            outsideRef.current &&
            !outsideRef.current.contains(event.target as Node)
        ) {
            onToggle();
        }
    };

    useEffect(() => {
        if (selector.name !== title) {
            onSelect?.(selector);
        }
    }, [selector]);
    useEffect(() => {
        document.addEventListener('mouseup', outsideClickHandler);
        return () =>
            document.removeEventListener('mouseup', outsideClickHandler);
    }, [onToggle]);
    useEffect(() => {
        if (!paramName || options.length === 0 || selector.id !== 0) return;
        const paramValue = Number(searchParams.get(paramName));
        if (!paramValue) return;
        const matchedOption = options.find(
            (option) => option.id === paramValue,
        );
        if (matchedOption) {
            setSelector({
                id: matchedOption.id,
                name: matchedOption.name,
            });
        }
    }, [paramName, options]);

    const optionListProps = options.map((option) => ({
        key: option.id,
        onClick: () => {
            setSelector({ id: option.id, name: option.name });
            onToggle();
        },
        children: option.name,
    }));

    return (
        <div ref={outsideRef} css={dropdownStyle}>
            <div css={selectorTitleStyle} onClick={onToggle}>
                <SearchBarText>{selector.name}</SearchBarText>
                <div css={selectorChevronButtonStyle(isOpenOptions)}>
                    <Icon {...chevronIconProps} />
                </div>
            </div>
            {isOpenOptions && (
                <div css={optionsStyle}>
                    <div css={optionTitleStyle}>
                        <SearchBarText>{title}</SearchBarText>
                        <div css={optionChevronButtonStyle}>
                            <Icon {...chevronIconProps} />
                        </div>
                    </div>
                    <ul>
                        {optionListProps.map((liProps) => (
                            <TextLi {...liProps} />
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}

const chevronIconProps = {
    name: 'chevron-down',
    width: 2,
    height: 2,
    color: 'grey-100',
};

const { colors } = theme;

const dropdownStyle = css`
    position: relative;
    width: max-content;
    cursor: pointer;
`;

const chevronButtonStyle = css`
    width: 1.5rem;
    height: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    transform-origin: 50% 50%;
`;

const selectorChevronButtonStyle = (isOpenOptions: boolean) =>
    css([
        chevronButtonStyle,
        `
    transform: rotate(${isOpenOptions ? 0 : -90}deg) scale(1.3);
    transition: transform 200ms ease;
  `,
    ]);

const optionChevronButtonStyle = css([
    chevronButtonStyle,
    `
    transform: scale(1.3);
  `,
]);

const selectorTitleStyle = css`
    display: flex;
    height: 3rem;
    padding: 0.5rem 1rem 0.5rem 1.5rem;
    box-sizing: border-box;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
    background-color: ${getColor({
        colors,
        colorString: 'greyOpacityWhite-70',
    })};
    backdrop-filter: blur(50px);
    border-radius: 6.25rem;
`;

const optionsStyle = css`
    position: absolute;
    top: calc(100% + 1.5rem);
    left: 50%;
    transform: translateX(-50%);
    z-index: 10;
    display: flex;
    flex-direction: column;
    width: max-content;
    padding: 1rem;
    border-radius: 1.5rem;
    background-color: ${getColor({
        colors,
        colorString: 'greyOpacityWhite-70',
    })};
    backdrop-filter: blur(50px);
`;

const optionTitleStyle = css`
    display: flex;
    flex-direction: row;
    margin-bottom: 1rem;
    gap: 0.5rem;
    color: ${getColor({
        colors,
        colorString: 'grey-100',
    })};
`;
