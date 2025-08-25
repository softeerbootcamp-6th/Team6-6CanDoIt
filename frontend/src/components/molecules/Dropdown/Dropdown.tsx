import SearchBarText from '../../atoms/Text/SearchBarText.tsx';
import TextLi from '../../atoms/Li/TextLi.tsx';
import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
import Icon from '../../atoms/Icon/Icons.tsx';

// 드롭다운의 제목, 옵션 리스트를 받고 렌더링
interface propsState {
    title: string;
    options: string[];
    isOpenOptions?: boolean;
}

export default function Dropdown(props: propsState) {
    const { title, options, isOpenOptions = 'false' } = props;

    return (
        <div css={dropdownStyle}>
            <div css={selectorTitleStyle}>
                <SearchBarText>{title}</SearchBarText>
                <div css={selectorChevronButtonStyle}>
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
                        {options.map((option) => (
                            <TextLi>{option}</TextLi>
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
`;

const chevronButtonStyle = css`
    width: 1.5rem;
    height: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    transform-origin: 50% 50%;
`;

const selectorChevronButtonStyle = css([
    chevronButtonStyle,
    `
    transform: rotate(-90deg) scale(1.3);
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
