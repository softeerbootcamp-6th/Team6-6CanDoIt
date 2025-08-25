import { css } from '@emotion/react';
import CommonText from '../../atoms/Text/CommonText';
import Icon from '../../atoms/Icon/Icons';

interface PropsState {
    onClose: () => void;
    type: string;
    courseAltitude?: number;
}

export default function DetailSideBarHeader({
    onClose,
    type,
    courseAltitude,
}: PropsState) {
    return (
        <div css={topStyles}>
            <span
                css={css`
                    display: flex;
                    gap: 0.5rem;
                `}
            >
                <CommonText
                    TextTag='span'
                    fontSize='label'
                    fontWeight='bold'
                    color='greyOpacity-40'
                >
                    {type}
                </CommonText>
                {courseAltitude && (
                    <CommonText
                        TextTag='span'
                        fontSize='label'
                        fontWeight='medium'
                        color='greyOpacity-60'
                    >
                        {`${courseAltitude}m`}
                    </CommonText>
                )}
            </span>
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
