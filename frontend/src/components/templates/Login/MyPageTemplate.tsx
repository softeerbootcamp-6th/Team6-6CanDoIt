import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';

const { colors, typography } = theme;

export default function MyPageTemplate() {
    return (
        <div css={wrapperStyles}>
            <div css={profileWrapper}>
                <span>이미지 칸</span>
                <div css={profileTextWrapper}>
                    <div>
                        <CommonText
                            TextTag='span'
                            fontSize='label'
                            fontWeight='bold'
                            color='grey-100'
                        >
                            한사랑 산악인
                        </CommonText>

                        <a css={linkStyles}>닉네임 변경</a>
                    </div>
                    <CommonText
                        TextTag='span'
                        fontSize='caption'
                        fontWeight='medium'
                        color='grey-60'
                    >
                        aasd2265@dlfivl
                    </CommonText>
                </div>
            </div>
            <button>개인 정보 변경</button>
        </div>
    );
}

const wrapperStyles = css`
    color: ${colors.grey[100]};
    display: flex;
    height: 8.25rem;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    padding: 1rem 1.5rem;
`;

const profileWrapper = css`
    display: flex;
`;

const profileTextWrapper = css`
    display: flex;
    flex-direction: column;
    gap: 0.8rem;
`;

const linkStyles = css`
    color: ${colors.grey[60]};
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.medium};
    margin-left: 0.5rem;
    font-style: normal;
    line-height: normal;
    text-decoration-line: underline;
    text-decoration-style: solid;
    text-decoration-skip-ink: auto;
    text-decoration-thickness: auto;
    text-underline-offset: auto;
    text-underline-position: from-font;
`;
