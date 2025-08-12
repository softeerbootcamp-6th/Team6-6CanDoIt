import LabelTextBox from '../../atoms/LabelTextBox/LabelTextBox.tsx';
import { css } from '@emotion/react';

interface propsState {
    labels: string[];
    isCut?: boolean;
}

export default function LabelList({ labels, isCut = true }: propsState) {
    return (
        <div css={isCut ? cutFilterLabelsStyle : uncutFilterLabelsStyle}>
            {labels.map((label) => (
                <LabelTextBox>{label}</LabelTextBox>
            ))}
        </div>
    );
}

const cutFilterLabelsStyle = css`
    display: flex;
    gap: 0.25rem;
    width: 100%;
    height: max-content;
    margin-top: auto;
    margin-bottom: auto;
    mask-image: linear-gradient(to right, black 80%, transparent);
`;

const uncutFilterLabelsStyle = css`
    width: 100%;
    height: max-content;
    margin-bottom: 2rem;
    display: flex;
    flex-wrap: wrap;
    gap: 0.5rem;
`;
