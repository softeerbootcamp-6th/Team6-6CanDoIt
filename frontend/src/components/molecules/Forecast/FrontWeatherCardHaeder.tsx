import { css } from '@emotion/react';
import Icon from '../../atoms/Icon/Icons';
import CloseButton from '../../atoms/Button/CloseButton';
import CommonText from '../../atoms/Text/CommonText';

interface PropsState {
    onClose: () => void;
    headerData: HeaderData[];
}

type HeaderData = {
    text: string;
    time: string;
    iconName: string;
};

export default function FrontWeatherCardHeader({
    onClose,
    headerData,
}: PropsState) {
    return (
        <div>
            <div
                css={css`
                    display: flex;
                    justify-content: space-between;
                `}
            >
                <div
                    css={css`
                        display: flex;
                        align-items: center;
                        gap: 0.5rem;
                    `}
                >
                    {headerData.map((item, index) => (
                        <div css={lineStyles} key={index}>
                            {textWithTime(item)}
                        </div>
                    ))}
                </div>
                <CloseButton onClose={onClose} />
            </div>
        </div>
    );
}

function textWithTime({
    iconName,
    text,
    time,
}: {
    iconName: string;
    text: string;
    time: string;
}) {
    return (
        <>
            <Icon name={iconName} width={1.5} height={1.5} color='grey-100' />
            <CommonText {...greyTextProps}>{text}</CommonText>
            <CommonText {...boldTempProps}>{`${time.slice(0, 5)}`}</CommonText>
        </>
    );
}

const greyTextProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'regular',
    color: 'greyOpacityWhite-50',
} as const;

const boldTempProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'grey-100',
} as const;

const lineStyles = css`
    display: flex;
    gap: 0.2rem;
    align-items: center;
    margin-right: 0.5rem;
`;
