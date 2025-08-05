import { Global, css } from '@emotion/react';
import emotionReset from 'emotion-reset';

export const GlobalStyle = () => {
    return (
        <Global
            styles={css`
                ${emotionReset}
            `}
        />
    );
};
