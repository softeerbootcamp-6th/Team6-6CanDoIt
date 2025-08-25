import CommonText from '../../atoms/Text/CommonText';
import Icon from '../../atoms/Icon/Icons';
import { css } from '@emotion/react';

export default function ScrollIndicator() {
    return (
        <div css={downStyles}>
            <CommonText {...textProps}>
                <span
                    css={css`
                        margin-bottom: 0.4rem;
                    `}
                >
                    스크롤하여 초단기 날씨를
                </span>
                <span>확인하세요</span>
            </CommonText>
            <Icon
                name='chevron-down-double'
                color='grey-100'
                width={3}
                height={3}
            />
        </div>
    );
}

const textProps = {
    TextTag: 'p',
    color: 'grey-100',
    fontWeight: 'medium',
    fontSize: 'caption',
    flexColoumn: true,
} as const;

const downStyles = css`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;
