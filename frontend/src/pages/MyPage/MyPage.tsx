import { css } from '@emotion/react';
import MyPageTemplate from '../../components/templates/Login/MyPageTemplate';

export default function MyPage() {
    return (
        <div css={wrapperStyles}>
            <MyPageTemplate />
        </div>
    );
}

const wrapperStyles = css`
    width: 75rem;
    margin: auto;
`;
