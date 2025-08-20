import Icon from '../../atoms/Icon/Icons.tsx';
import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';
import { theme } from '../../../theme/theme.ts';
import { useEffect, useRef, useState } from 'react';

interface imageFile {
    imageFile: File;
    previewUrl: string;
}

export default function ImageInputField() {
    const inputRef = useRef<HTMLInputElement | null>(null);
    const [dragActive, setDragActive] = useState(false);
    const [image, setImage] = useState<imageFile | null>(null);

    const handleFile = (file?: File) => {
        if (!file) return;
        if (!file.type.startsWith('image/')) return;
        const previewUrl = URL.createObjectURL(file);
        setImage((prev) => {
            if (prev) URL.revokeObjectURL(prev.previewUrl);
            return { imageFile: file, previewUrl };
        });
    };

    const openPicker = () => inputRef.current?.click();

    const onDragOver = (e: React.DragEvent) => {
        e.preventDefault();
        setDragActive(true);
    };
    const onDragLeave = (e: React.DragEvent) => {
        e.preventDefault();
        setDragActive(false);
    };
    const onDrop = (e: React.DragEvent) => {
        e.preventDefault();
        setDragActive(false);
        handleFile(e.dataTransfer.files?.[0]);
    };

    useEffect(() => {
        return () => {
            if (image) URL.revokeObjectURL(image.previewUrl);
        };
    }, [image]);

    return (
        <div
            css={[imageAttachmentStyle, dragActive && dragStyle]}
            onClick={openPicker}
            onDragOver={onDragOver}
            onDragLeave={onDragLeave}
            onDrop={onDrop}
        >
            {!image ? (
                <>
                    <Icon
                        name='download-01'
                        width={2.5}
                        height={2.5}
                        color='grey-70'
                    />
                    <div css={imageCaptionStyle}>
                        <CommonText {...textProps}>
                            사진을 첨부해주세요
                        </CommonText>
                    </div>
                </>
            ) : (
                <img
                    src={image.previewUrl}
                    alt='업로드 이미지'
                    css={previewStyle}
                />
            )}

            <input
                ref={inputRef}
                type='file'
                accept='image/*'
                multiple={false}
                name='image'
                required
                style={{ display: 'none' }}
                onChange={(e) => handleFile(e.target.files?.[0])}
            />
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
`;

const imageCaptionStyle = css`
    width: 7.8125rem;
    text-align: center;
    word-break: keep-all;
`;

const dragStyle = css`
    outline-color: ${colors.primary.normal};
    background-color: ${colors.grey[40]};
`;

const previewStyle = css`
    width: 100%;
    height: 100%;
    border-radius: 1.875rem;
    object-fit: cover;
`;
