import { css } from '@emotion/react';
import IconLink from '../../atoms/Link/IconLink';

export default function IconNavList() {
    return (
        <div css={wrapperStyles}>
            <IconLink to='/myPage' name='bell-01' />
            <IconLink to='/login' name='user-circle' />
        </div>
    );
}

const wrapperStyles = css`
    display: flex;
    gap: 0.8rem;
`;
