import { Global, css } from '@emotion/react';
import emotionReset from 'emotion-reset';

export const GlobalStyle = () => {
    return (
        <Global
            styles={css`
                ${emotionReset};

                @font-face {
                    font-family: 'Pretendard';
                    font-weight: 400;
                    font-style: normal;
                    font-display: swap;
                    src: url('/fonts/Pretendard-Regular.woff2') format('woff2');
                }
                @font-face {
                    font-family: 'Pretendard';
                    font-weight: 500;
                    font-style: normal;
                    font-display: swap;
                    src: url('/fonts/Pretendard-Medium.woff2') format('woff2');
                }
                @font-face {
                    font-family: 'Pretendard';
                    font-weight: 700;
                    font-style: normal;
                    font-display: swap;
                    src: url('/fonts/Pretendard-Bold.woff2') format('woff2');
                }

                html,
                body {
                    font-synthesis: none;
                }
                body {
                    font-family: 'Pretendard', system-ui, sans-serif;
                    font-weight: 400;
                    -ms-overflow-style: none;

                    ::-webkit-scrollbar {
                        display: none;
                    }
                }
            `}
        />
    );
};
