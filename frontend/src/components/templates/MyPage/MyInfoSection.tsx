import { css } from '@emotion/react';
import { theme } from '../../../theme/theme';
import CommonText from '../../atoms/Text/CommonText';
import dummyImg from '../../../assets/mainImg.png';
import useApiQuery from '../../../hooks/useApiQuery';
import useNicknameEditor from '../../../hooks/useNicknamEditor.ts';
import useProfileImageUploader from '../../../hooks/useProfileImageUploader.ts';
import PendingModal from '../../molecules/Modal/ReportPendingModal.tsx';

interface PropsState {
    onValid: () => void;
}

interface UserData {
    nickname: string;
    loginId: string;
    imageUrl?: string;
}

export default function MyInfoSection({ onValid }: PropsState) {
    const {
        isEditingNickName,
        setIsEditingNickName,
        inputNickNameRef,
        handleSave,
    } = useNicknameEditor(onValid);

    const { fileInputRef, handleFileClick, handleFileChange } =
        useProfileImageUploader(onValid);

    const { data: myInfoData } = useApiQuery<UserData>(
        `/user`,
        {},
        {
            retry: false,
            enabled: true,
        },
    );

    const handleLogout = () => {
        localStorage.removeItem('accessToken');
        sessionStorage.removeItem('accessToken');

        window.location.reload();
    };

    if (!myInfoData) return <PendingModal />;

    const { nickname, loginId, imageUrl = dummyImg } = myInfoData;

    return (
        <div css={wrapperStyles}>
            <div css={profileWrapper}>
                <div css={imgWrappStyles} onClick={handleFileClick}>
                    <img src={imageUrl} css={imgStyles}></img>
                </div>
                <div css={profileTextWrapper}>
                    <div
                        css={css`
                            display: flex;
                        `}
                    >
                        {isEditingNickName ? (
                            <form>
                                <input
                                    type='text'
                                    css={inputStyles}
                                    ref={inputNickNameRef}
                                    defaultValue={nickname}
                                />
                                <button
                                    css={changeButtonStyles}
                                    onClick={handleSave}
                                >
                                    변경
                                </button>

                                <button
                                    css={changeButtonStyles}
                                    onClick={() => setIsEditingNickName(false)}
                                >
                                    취소
                                </button>
                            </form>
                        ) : (
                            <CommonText
                                TextTag='span'
                                fontSize='label'
                                fontWeight='bold'
                                color='grey-100'
                            >
                                {nickname}
                            </CommonText>
                        )}
                        {!isEditingNickName && (
                            <button
                                css={linkStyles}
                                onClick={() => setIsEditingNickName(true)}
                            >
                                닉네임 변경
                            </button>
                        )}
                    </div>
                    <CommonText
                        TextTag='span'
                        fontSize='caption'
                        fontWeight='medium'
                        color='grey-60'
                    >
                        {loginId}
                    </CommonText>
                </div>
            </div>
            <button css={buttonStyles} onClick={handleLogout}>
                로그아웃
            </button>
            <input
                type='file'
                accept='image/*'
                ref={fileInputRef}
                style={{ display: 'none' }}
                onChange={handleFileChange}
            />
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
    all: unset;
    color: ${colors.grey[60]};
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.medium};
    margin-left: 0.5rem;
    line-height: normal;
    text-decoration-line: underline;
    text-decoration-style: solid;
    text-decoration-skip-ink: auto;
    text-decoration-thickness: auto;
    text-underline-offset: 4px;
    cursor: pointer;

    &:hover {
        opacity: 0.7;
        color: ${colors.grey[100]};
    }
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
    cursor: pointer;
    &:hover {
        opacity: 0.7;
    }
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
    cursor: pointer;
    &:hover {
        opacity: 0.7;
    }
`;

const imgStyles = css`
    width: 100%;
    height: 100%;
    object-fit: cover;
`;

const inputStyles = css`
    all: unset;
    border-bottom: 1px solid ${colors.grey[40]};
    font-size: 1rem;
    flex: 1;
    outline: none;
`;

const changeButtonStyles = css`
    all: unset;
    padding: 0.25rem 0.5rem;
    font-size: ${typography.fontSize.caption};
    font-weight: ${typography.fontWeight.bold};
    color: ${colors.grey[60]};
    border-radius: 0.5rem;
    cursor: pointer;

    &:hover {
        opacity: 0.7;
        color: ${colors.grey[100]};
    }
`;
