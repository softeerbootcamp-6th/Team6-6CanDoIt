import { css } from '@emotion/react';
import ChipButton from '../../molecules/Button/ChipButton.tsx';

interface PropsState {
    hidden?: boolean;
    items?: { text: string; iconName: string }[];
    onFilterClick?: (event: React.MouseEvent<HTMLElement>) => void;
}

export default function SearchSectionChipsRow({
    hidden = false,
    items = defaultChipItems,
    onFilterClick,
}: PropsState) {
    return (
        <div css={hidden ? chipsCloneStyle : chipsStyle}>
            {items.map((item) => (
                <ChipButton
                    onClick={item.text === '필터' ? onFilterClick : undefined}
                    text={item.text}
                    iconName={item.iconName}
                />
            ))}
        </div>
    );
}

const defaultChipItems = [
    { text: '필터', iconName: 'filter-lines' },
    { text: '알림받기', iconName: 'bell-plus' },
];

const chipsStyleBase = css`
    display: flex;
    align-items: center;
    gap: 1rem;
`;

const chipsStyle = css`
    ${chipsStyleBase};
`;

const chipsCloneStyle = css`
    ${chipsStyleBase};
    visibility: hidden;
    pointer-events: none;
`;
