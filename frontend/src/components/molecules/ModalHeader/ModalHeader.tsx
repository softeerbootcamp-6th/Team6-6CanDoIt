import CommonText from '../../atoms/Text/CommonText.tsx';
import { css } from '@emotion/react';

interface propsState {
    title: string;
    description: string;
    children?: React.ReactNode;
}

export default function ModalHeader(props: propsState) {
    const { title, description, children } = props;

    return (
        <div css={modalHeaderStyle}>
            <CommonText TextTag='span' fontSize='label' fontWeight='bold'>
                {title}
            </CommonText>
            <CommonText {...descriptionTextProps}>{description}</CommonText>
            {children}
        </div>
    );
}

const descriptionTextProps = {
    TextTag: 'p',
    fontSize: 'body',
    fontWeight: 'medium',
    color: 'greyOpacityWhite-30',
} as const;

const modalHeaderStyle = css`
    width: 34.125rem;
    display: flex;
    flex-direction: column;
    align-items: start;
    gap: 1rem 0.25rem;
    margin-top: 1.5rem;
    margin-bottom: 1.5rem;
`;
