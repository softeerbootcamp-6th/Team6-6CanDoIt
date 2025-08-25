import '@emotion/react';
import type { Theme as AppTheme } from '../theme/theme.ts';

declare module '@emotion/react' {
    // 기존 빈 Theme에 우리 타입을 합침
    export interface Theme extends AppTheme {}
}
