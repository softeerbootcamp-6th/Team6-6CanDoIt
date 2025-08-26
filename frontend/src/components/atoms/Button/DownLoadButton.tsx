import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

import Icon from '../Icon/Icons';

interface PropsState {
    onClick: () => void;
}

export default function DownloadButton({ onClick }: PropsState) {
    return (
        <button
            css={storeBtnStyles}
            onClick={(e) => {
                e.stopPropagation();
                onClick();
            }}
        >
            <Icon {...downloadIconProps} />
        </button>
    );
}

const downloadIconProps = {
    name: 'download-02',
    width: 1.4,
    height: 1.4,
    color: 'grey-100',
};

const { colors } = theme;

const storeBtnStyles = css`
    all: unset;
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    top: 3rem;
    right: 5%;
    width: 3rem;
    height: 3rem;
    border-radius: 100%;
    background-color: ${colors.greyOpacityWhite[70]};
    padding: 0 3px 5px 0;
    box-sizing: border-box;
    cursor: pointer;
`;
