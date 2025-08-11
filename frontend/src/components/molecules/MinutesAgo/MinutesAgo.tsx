import CommonText from '../../atoms/Text/CommonText.tsx';

export default function MinutesAgo({ value }: { value: number }) {
    return <CommonText {...minutesAgoProps}>{`${value}분전`}</CommonText>;
}

const minutesAgoProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'medium',
    color: 'greyOpacityWhite-60',
    lineHeight: 1.3,
} as const;
