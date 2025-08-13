import CommonText from '../../atoms/Text/CommonText';
import mainImg from '../../../assets/mainImg.png';
import LoginForm from '../../organisms/Login/LoginForm.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';

const { colors, typography } = theme;

export default function LoginFormSection() {
    return (
        <>
            <h1>
                <img width={156} height={40} src={mainImg}></img>
            </h1>
            <CommonText
                TextTag='p'
                color='grey-70'
                fontSize='body'
                fontWeight='bold'
            >
                국립공원을 오르는 오늘을 위한 기상 예보
            </CommonText>
            <LoginForm />
            <a href='#' css={LinkStyles}>
                이메일 가입
            </a>
        </>
    );
}

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
