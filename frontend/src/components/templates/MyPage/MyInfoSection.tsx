import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import dummyImg from '../../../assets/mainImg.png';

interface PropsState {
    userData: UserData;
}

interface UserData {
    userNickName: string;
    userId: string;
    userImage?: string;
}

export default function MyInfoSection({ userData }: PropsState) {
    const { userNickName, userId, userImage = dummyImg } = userData;

    return (
        <div css={wrapperStyles}>
            <div css={profileWrapper}>
                <div css={imgWrappStyles}>
                    <img src={userImage} css={imgStyles}></img>
                </div>
                <div css={profileTextWrapper}>
                    <div>
                        <CommonText
                            TextTag='span'
                            fontSize='label'
                            fontWeight='bold'
                            color='grey-100'
                        >
                            {userNickName}
                        </CommonText>

                        <a css={linkStyles}>닉네임 변경</a>
                    </div>
                    <CommonText
                        TextTag='span'
                        fontSize='caption'
                        fontWeight='medium'
                        color='grey-60'
                    >
                        {userId}
                    </CommonText>
                </div>
            </div>
            <button css={buttonStyles}>개인 정보 변경</button>
        </div>
    );
}

const { colors, typography } = theme;

const wrapperStyles = css`
    color: ${colors.grey[100]};
    background-color: ${colors.grey[20]};
    border-radius: 1.5rem;
    display: flex;
    height: 8.25rem;
    justify-content: space-between;
    align-items: center;
    padding: 1rem 1.5rem;
    box-sizing: border-box;
`;

const profileWrapper = css`
    display: flex;
    align-items: center;
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

const buttonStyles = css`
    all: unset;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.bold};
    background-color: ${colors.grey[90]};
    border-radius: 0.75rem;
    height: 3.5rem;
    width: 10rem;
    line-height: 150%;
    color: ${colors.grey[20]};
`;

const imgWrappStyles = css`
    width: 6rem;
    height: 6rem;
    border-radius: 100%;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 1rem;
`;

const imgStyles = css`
    width: 100%;
    height: 100%;
    object-fit: cover;
`;
