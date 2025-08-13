import { css } from '@emotion/react';
import ChipButton from '../../molecules/Button/ChipButton.tsx';

export default function ChipsRow({
    hidden = false,
    items = defaultChipItems,
}: {
    hidden?: boolean;
    items?: { text: string; iconName: string }[];
}) {
    return (
        <div css={hidden ? chipsCloneStyle : chipsStyle}>
            {items.map((it) => (
                <ChipButton text={it.text} iconName={it.iconName} />
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
