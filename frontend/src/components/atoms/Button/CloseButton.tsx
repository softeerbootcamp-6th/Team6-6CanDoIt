import { css } from '@emotion/react';
import Icon from '../Icon/Icons';

interface PropsState {
    onClose: () => void;
}

export default function CloseButton({ onClose }: PropsState) {
    return (
        <button css={closeBtn} onClick={onClose}>
            <Icon name='x' width={1.4} height={1.4} color='grey-100' />
        </button>
    );
}

const closeBtn = css`
    all: unset;
    cursor: pointer;
    padding: 0.5rem;
    border-radius: 50%;
`;
