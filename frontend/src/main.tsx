import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import { ThemeProvider } from '@emotion/react';
import { theme } from './theme/theme.ts';
import './styles/fonts.css';
import './index.css';
import { GlobalStyle } from './styles/GlobalStyle.tsx';

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <ThemeProvider theme={theme}>
            <GlobalStyle />
            <App />
        </ThemeProvider>
    </StrictMode>,
);
