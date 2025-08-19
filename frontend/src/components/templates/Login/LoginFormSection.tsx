import CommonText from '../../atoms/Text/CommonText';
import mainImg from '../../../assets/mainImg.png';
import LoginForm from '../../organisms/Login/LoginForm.tsx';
import { css, keyframes } from '@emotion/react';
import { theme } from '../../../theme/theme';
import { NavLink } from 'react-router-dom';

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
            <div css={WrapperStyles}>
                <LoginForm />
                <NavLink to='/register' css={LinkStyles}>
                    회원 가입
                </NavLink>
            </div>
        </>
    );
}

const slideFadeIn = keyframes`
  0% {
    transform: translateY(-70px);
    opacity: 0;
  }
  100% {
    transform: translateY(0px);
    opacity: 1;
  }
`;

const WrapperStyles = css`
    animation: ${slideFadeIn} 0.6s cubic-bezier(0.25, 1, 0.5, 1) forwards;
    display: flex;
    flex-direction: column;
    gap: 2rem;
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
