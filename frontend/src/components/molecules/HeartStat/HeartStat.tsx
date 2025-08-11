import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';

export default function HeartStat({ count }: { count: number }) {
    return (
        <div css={heartStyle}>
            <Icon {...heartIconProps} />
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
