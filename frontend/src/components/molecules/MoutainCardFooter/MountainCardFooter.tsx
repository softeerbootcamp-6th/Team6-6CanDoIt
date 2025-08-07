import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';
import { getColor } from '../../../utils/utils.ts';
import { theme } from '../../../theme/theme.ts';

export default function MountainCardFooter({
    mountainName,
}: {
    mountainName: string;
}) {
    return (
        <div>
            <CommonText {...mountainNameProps}>{mountainName}</CommonText>
            <button css={textButtonStyle}>
                <CommonText {...textButtonProps}>날씨 보러가기</CommonText>
            </button>
        </div>
    );
}

const mountainNameProps = {
    TextTag: 'p',
    fontSize: 'headline',
    fontWeight: 'bold',
    color: 'grey-100',
} as const;

const textButtonProps = {
    TextTag: 'p',
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
