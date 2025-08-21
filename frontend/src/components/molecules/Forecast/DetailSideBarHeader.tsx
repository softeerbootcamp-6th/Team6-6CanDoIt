import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import Icon from '../../atoms/Icon/Icons';

interface PropsState {
    onClose: () => void;
    type: string;
}

export default function DetailSideBarHeader({ onClose, type }: PropsState) {
    return (
        <div css={topStyles}>
            <CommonText
                TextTag='span'
                fontSize='label'
                fontWeight='bold'
                color='greyOpacity-40'
            >
                {type}
            </CommonText>
            <button
                css={css`
                    all: unset;
                    cursor: pointer;
                `}
                onClick={onClose}
            >
                <Icon name='x' color='grey-0' width={1.5} height={1.5} />
            </button>
        </div>
    );
}

const topStyles = css`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;
