import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';

export default function ImageInputField() {
    return (
        <div css={imageAttachmentStyle}>
            <Icon name='download-01' width={2.5} height={2.5} color='grey-70' />
            <div css={imageCaptionStyle}>
                <CommonText {...textProps}>사진을 첨부해주세요</CommonText>
            </div>
        </div>
    );
}

const textProps = {
    TextTag: 'span',
    fontSize: 'label',
    fontWeight: 'medium',
    color: 'grey-70',
    lineHeight: 1.5,
} as const;

const { colors } = theme;

const imageAttachmentStyle = css`
    width: 23.125rem;
    height: 23.125rem;
    background-color: ${colors.grey[50]};
    border-radius: 1.875rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 2rem;
`;

const imageCaptionStyle = css`
    width: 7.8125rem;
    text-align: center;
    word-break: keep-all;
`;
