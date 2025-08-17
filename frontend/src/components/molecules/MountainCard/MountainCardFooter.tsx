import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';
import { HeadlineHeading } from '../../atoms/Heading/Heading.tsx';

interface PropsState {
    mountainName: string;
}

export default function MountainCardFooter({ mountainName }: PropsState) {
    return (
        <div>
            <HeadlineHeading HeadingTag='h3'>{mountainName}</HeadlineHeading>
            <button css={textButtonStyle}>
                <CommonText {...textButtonProps}>날씨 보러가기</CommonText>
            </button>
        </div>
    );
}

const textButtonProps = {
    TextTag: 'span',
    fontSize: 'caption',
    fontWeight: 'bold',
    color: 'grey-20',
} as const;

const { colors } = theme;

const textButtonStyle = css`
    width: 100%;
    margin-top: 1rem;
    padding: 0.5rem 1.25rem;
    border: none;
    border-radius: 2.5rem;
    background-color: ${getColor({ colors, colorString: 'grey-98' })};
    cursor: pointer;
    text-align: left;
`;
