import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import { LabelHeading } from '../../atoms/Heading/Heading';
import CommonText from '../../atoms/Text/CommonText';
const { colors, typography } = theme;

export default function RegisterHeader() {
    return (
        <div css={registerHeaderStyles}>
            <LabelHeading HeadingTag='h1'>회원가입</LabelHeading>
            <div css={boxStyles}>
                <CommonText TextTag='p' fontSize='caption' fontWeight='medium'>
                    이미 아이디가 있으신가요?
                </CommonText>
                <a css={LinkStyles}>로그인</a>
            </div>
        </div>
    );
}

const registerHeaderStyles = css`
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
`;

const boxStyles = css`
    display: flex;
    gap: 0.5rem;
`;

const LinkStyles = css`
    color: ${colors.grey[100]};
    text-align: center;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.regular};
    text-decoration-line: underline;
    text-decoration-style: solid;
    text-underline-position: from-font;
    cursor: pointer;
`;
