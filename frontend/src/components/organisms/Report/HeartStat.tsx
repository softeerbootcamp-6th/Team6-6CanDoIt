import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';

interface propsState {
    count: number;
}

export default function HeartStat({ count }: propsState) {
    return (
        <div css={heartStyle}>
            <button css={buttonStyle}>
                <Icon {...heartIconProps} />
            </button>
            <CommonText TextTag='span' fontSize='body' fontWeight='bold'>
                {count}
            </CommonText>
        </div>
    );
}

const heartIconProps = {
    name: 'heart-rounded',
    width: 1.5,
    height: 1.5,
    color: 'grey-100',
};

const buttonStyle = css`
    background: none;
    border: none;
    padding: 0;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
`;

const heartStyle = css`
    width: 100%;
    height: max-content;
    display: flex;
    align-items: center;
    justify-content: start;
    gap: 0.5rem;
    margin-top: 1rem;
    margin-bottom: auto;
`;
