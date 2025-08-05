export const theme = {
    colors: {
        /** 회색 계조 */
        grey: {
            0: 'var(--grey-0)',
            10: 'var(--grey-10)',
            20: 'var(--grey-20)',
            30: 'var(--grey-30)',
            40: 'var(--grey-40)',
            50: 'var(--grey-50)',
            60: 'var(--grey-60)',
            70: 'var(--grey-70)',
            80: 'var(--grey-80)',
            90: 'var(--grey-90)',
            98: 'var(--grey-98)',
            100: 'var(--grey-100)',
        },
        /** 검정 투명도 단계 */
        greyOpacity: {
            0: 'var(--grey-opacity-0)',
            10: 'var(--grey-opacity-10)',
            20: 'var(--grey-opacity-20)',
            30: 'var(--grey-opacity-30)',
            40: 'var(--grey-opacity-40)',
            50: 'var(--grey-opacity-50)',
            60: 'var(--grey-opacity-60)',
            70: 'var(--grey-opacity-70)',
            80: 'var(--grey-opacity-80)',
            90: 'var(--grey-opacity-90)',
            98: 'var(--grey-opacity-98)',
            100: 'var(--grey-opacity-100)',
        },
        /** 흰색 투명도 단계 */
        greyOpacityWhite: {
            0: 'var(--grey-opacity-white-0)',
            10: 'var(--grey-opacity-white-10)',
            20: 'var(--grey-opacity-white-20)',
            30: 'var(--grey-opacity-white-30)',
            40: 'var(--grey-opacity-white-40)',
            50: 'var(--grey-opacity-white-50)',
            60: 'var(--grey-opacity-white-60)',
            70: 'var(--grey-opacity-white-70)',
            80: 'var(--grey-opacity-white-80)',
            90: 'var(--grey-opacity-white-90)',
            98: 'var(--grey-opacity-white-98)',
            100: 'var(--grey-opacity-white-100)',
        },
        /** 포인트(Primary) */
        primary: {
            normal: 'var(--primary-normal)',
        },
        /** 상태(Status) */
        status: {
            normal: {
                excellent: 'var(--status-normal-excellent)',
                good: 'var(--status-normal-good)',
                average: 'var(--status-normal-average)',
                bad: 'var(--status-normal-bad)',
            },
            regular: {
                excellent: 'var(--status-regular-excellent)',
                good: 'var(--status-regular-good)',
                average: 'var(--status-regular-average)',
                bad: 'var(--status-regular-bad)',
            },
            light: {
                excellent: 'var(--status-light-excellent)',
                good: 'var(--status-light-good)',
                average: 'var(--status-light-average)',
                bad: 'var(--status-light-bad)',
            },
        },
        /** 날씨 아이콘용 보조 색 */
        accentWeather: {
            sunny: 'var(--accent-weather-sunny)',
            heatwave: 'var(--accent-weather-heatwave)',
            dust: 'var(--accent-weather-dust)',
            snow: 'var(--accent-weather-snow)',
            cloudy: 'var(--accent-weather-cloudy)',
            rain: 'var(--accent-weather-rain)',
            windy: 'var(--accent-weather-windy)',
        },
    },

    typography: {
        displayBold: {
            fontWeight: 'var(--font-weight-bold)',
            fontSize: 'var(--font-size-display)',
            lineHeight: 'auto',
        },
        headlineBold: {
            fontWeight: 'var(--font-weight-bold)',
            fontSize: 'var(--font-size-headline)',
            lineHeight: 'auto',
        },
        titleBold: {
            fontWeight: 'var(--font-weight-bold)',
            fontSize: 'var(--font-size-title)',
            lineHeight: 'auto',
        },
        labelBold: {
            fontWeight: 'var(--font-weight-bold)',
            fontSize: 'var(--font-size-label)',
            lineHeight: 'auto',
        },
        bodyBold: {
            fontWeight: 'var(--font-weight-bold)',
            fontSize: 'var(--font-size-body)',
            lineHeight: 'auto',
        },
        captionBold: {
            fontWeight: 'var(--font-weight-bold)',
            fontSize: 'var(--font-size-caption)',
            lineHeight: 'auto',
        },

        displayMedium: {
            fontWeight: 'var(--font-weight-medium)',
            fontSize: 'var(--font-size-display)',
            lineHeight: 'auto',
        },
        headlineMedium: {
            fontWeight: 'var(--font-weight-medium)',
            fontSize: 'var(--font-size-headline)',
            lineHeight: 'auto',
        },
        titleMedium: {
            fontWeight: 'var(--font-weight-medium)',
            fontSize: 'var(--font-size-title)',
            lineHeight: 'auto',
        },
        labelMedium: {
            fontWeight: 'var(--font-weight-medium)',
            fontSize: 'var(--font-size-label)',
            lineHeight: 'auto',
        },
        bodyMedium: {
            fontWeight: 'var(--font-weight-medium)',
            fontSize: 'var(--font-size-body)',
            lineHeight: 'auto',
        },
        captionMedium: {
            fontWeight: 'var(--font-weight-medium)',
            fontSize: 'var(--font-size-caption)',
            lineHeight: 'auto',
        },
    },
} as const;

export type Theme = typeof theme;
