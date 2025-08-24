interface CourseForcast {
    startCard: CardData;
    arrivalCard: CardData;
    adjustedArrivalCard: CardData;
    descentCard: CardData;
    courseAltitude: number;
    recommendComment: string;
    adjustedRecommendComment: string;
}

interface CardData {
    dateTime: string;
    hikingActivity: HikingActivityStatus;
    temperature: number;
    apparentTemperature: number;
    temperatureDescription: string;
    precipitation: string;
    probabilityDescription: string;
    precipitationType: string;
    sky: string;
    skyDescription: string;
    windSpeed: number;
    windSpeedDescription: string;
    humidity: number;
    humidityDescription: string;
    highestTemperature: number;
    lowestTemperature: number;
    title?: string;
}

type HikingActivityStatus = '좋음' | '매우좋음' | '나쁨' | '보통';

const detailInfoSectionData: CourseForcast = {
    courseAltitude: 850,
    recommendComment: '오늘은 날씨가 좋아 산행하기 적합합니다.',
    adjustedRecommendComment: '조금 더 추워질 수 있으니 옷을 준비하세요.',
    startCard: {
        dateTime: '2025-08-21T06:00:00',
        hikingActivity: '좋음',
        temperature: 20,
        apparentTemperature: 19.5,
        temperatureDescription: '쾌적한 기온입니다.',
        precipitation: '0.0',
        probabilityDescription: '비 올 가능성 낮음',
        precipitationType: 'NONE',
        sky: 'SUNNY',
        skyDescription: '맑음',
        windSpeed: 1.2,
        windSpeedDescription: '약한 바람',
        humidity: 55,
        humidityDescription: '적정 습도',
        highestTemperature: 26,
        lowestTemperature: 17,
        title: '시작지점',
    },
    arrivalCard: {
        dateTime: '2025-08-21T09:00:00',
        hikingActivity: '좋음',
        temperature: 23,
        apparentTemperature: 22.5,
        temperatureDescription: '조금 따뜻한 편입니다.',
        precipitation: '0.1',
        probabilityDescription: '약간의 비 가능성',
        precipitationType: 'SHOWER',
        sky: 'PARTLY_CLOUDY',
        skyDescription: '부분적으로 흐림',
        windSpeed: 1.8,
        windSpeedDescription: '약간 강한 바람',
        humidity: 50,
        humidityDescription: '쾌적한 습도',
        highestTemperature: 27,
        lowestTemperature: 18,
        title: '도착지점',
    },
    adjustedArrivalCard: {
        dateTime: '2025-08-21T09:00:00',
        hikingActivity: '좋음',
        temperature: 22,
        apparentTemperature: 21.5,
        temperatureDescription: '살짝 시원함',
        precipitation: '0.0',
        probabilityDescription: '비 가능성 낮음',
        precipitationType: 'NONE',
        sky: 'CLOUDY',
        skyDescription: '흐림',
        windSpeed: 1.5,
        windSpeedDescription: '약한 바람',
        humidity: 52,
        humidityDescription: '적정 습도',
        highestTemperature: 26,
        lowestTemperature: 17,
        title: '조정 도착지점',
    },
    descentCard: {
        dateTime: '2025-08-21T12:00:00',
        hikingActivity: '좋음',
        temperature: 21,
        apparentTemperature: 20.5,
        temperatureDescription: '쾌적한 기온',
        precipitation: '0.0',
        probabilityDescription: '비 가능성 없음',
        precipitationType: 'NONE',
        sky: 'SUNNY',
        skyDescription: '맑음',
        windSpeed: 1.0,
        windSpeedDescription: '약한 바람',
        humidity: 53,
        humidityDescription: '적정 습도',
        highestTemperature: 25,
        lowestTemperature: 16,
        title: '끝지점',
    },
};

const summaryInfoSectionData = {
    courseImageUrl: 'https://cdn.example.com/images/course01.png',
    duration: 2.5,
    distance: 4.8,
    sunrise: '09:00:00',
    sunset: '09:00:00',
    hikingActivityStatus: '좋음',
} as const;

const frontCard = {
    mountainId: 12,
    mountainName: '치악산',
    mountainImageUrl: 'https://cdn.example.com/images/chiak.png',
    courseId: 301,
    courseName: '비로봉 왕복 코스',
    courseImageUrl: 'https://cdn.example.com/images/course301.png',
    distance: 8.6,
    duration: 4.2,
    sunrise: '05:51:00',
    sunset: '19:51:00',
};

const backCard = {
    date: '2025-08-19',
    startTime: '13:00:00',
    descentTime: '16:00:00',
    mountainName: '북한산',
    courseName: '백운대 코스',
    distance: 2.0,
    startForecast: {
        temperature: 28.0,
        windSpeed: 4.4,
        apparentTemperature: 30.0,
        precipitationProbability: 20.0,
        sky: '조금 흐린 하늘',
        humidity: 80.0,
    },
    arrivalForecast: {
        temperature: 30.0,
        windSpeed: 3.5,
        apparentTemperature: 31.3,
        precipitationProbability: 60.0,
        sky: '조금 흐린 하늘',
        humidity: 70.0,
    },
    descentForecast: {
        temperature: 28.0,
        windSpeed: 4.2,
        apparentTemperature: 29.3,
        precipitationProbability: 20.0,
        sky: '조금 흐린 하늘',
        humidity: 70.0,
    },
    highestTemperature: 30.5,
    lowestTemperature: 22.2,
    hikingActivityStatus: '나쁨',
} as const;

const userInfoData = {
    userNickName: '한사랑산악인',
    userId: 'qweasdasdsda',
};

export {
    userInfoData,
    detailInfoSectionData,
    summaryInfoSectionData,
    backCard,
    frontCard,
};
