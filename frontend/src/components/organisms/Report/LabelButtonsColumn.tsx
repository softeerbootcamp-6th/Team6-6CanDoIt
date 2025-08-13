import CommonText from '../../atoms/Text/CommonText.tsx';
import FilterLabelButton from '../../atoms/Button/FilterLabelButton.tsx';
import { css } from '@emotion/react';

interface propsState {
    title: string;
    filterLabels: string[];
}

export default function LabelButtonsColumn(props: propsState) {
    const { title, filterLabels } = props;

    return (
        <div css={columnStyle}>
            <CommonText {...textProps}>{title}</CommonText>
            <ul css={listStyle}>
                {filterLabels.map((label) => (
                    <li>
                        <FilterLabelButton>{label}</FilterLabelButton>
                    </li>
                ))}
            </ul>
        </div>
    );
}

const textProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'grey-90',
} as const;

const columnStyle = css`
    display: flex;
    flex-direction: column;
    justify-content: start;
    width: max-content;
    height: max-content;
    gap: 1rem;
`;

const listStyle = css`
    width: max-content;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
`;
