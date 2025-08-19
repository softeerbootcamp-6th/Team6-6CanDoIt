import { css } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons';
import CommonText from '../../atoms/Text/CommonText';

interface PropsState {
    sunriseTime: string;
    sunsetTime: string;
}

export default function SunSchedule({ sunriseTime, sunsetTime }: PropsState) {
    return (
        <div css={lineStyles}>
            <span>
                <Icon
                    name='sunrise'
                    color='grey-100'
                    width={1.5}
                    height={1.5}
                />

                <CommonText
                    {...textProps}
                >{`일출시간 : ${sunriseTime}`}</CommonText>
            </span>
            <span>
                <Icon name='sunset' color='grey-100' width={1.5} height={1.5} />
                <CommonText
                    {...textProps}
                >{`일몰시간 : ${sunsetTime}`}</CommonText>
            </span>
        </div>
    );
}

const lineStyles = css`
    display: flex;

    & > span {
        display: flex;
        align-items: center;
    }

    gap: 2rem;
    & svg {
        margin-right: 0.5rem;
    }
`;

const textProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'regular',
    color: 'greyOpacityWhite-50',
} as const;
