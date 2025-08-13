import { css } from '@emotion/react';
import TextWithIcon from '../Text/TextWithIcon.tsx';
import { theme } from '../../../theme/theme.ts';

const { colors } = theme;

export default function MapButton() {
    return (
        <button css={buttonStyles}>
            <TextWithIcon
                text='지도로 이동'
                iconName='marker-pin-01'
                color='grey-90'
            />
        </button>
    );
}

const buttonStyles = css`
    border-radius: 999px;
    padding: 0.75rem 1.5rem;
    background-color: ${colors.grey[20]};
    border: 1px solid ${colors.greyOpacityWhite[90]};
`;
