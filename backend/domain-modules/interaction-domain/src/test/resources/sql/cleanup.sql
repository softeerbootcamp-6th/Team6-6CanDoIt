-- 외래 키 제약 조건 비활성화
SET FOREIGN_KEY_CHECKS = 0;

-- 모든 테이블 데이터 삭제 (TRUNCATE는 AUTO_INCREMENT 값까지 초기화하여 가장 깔끔합니다)
TRUNCATE TABLE grid;
TRUNCATE TABLE image;
TRUNCATE TABLE mountain;
TRUNCATE TABLE course;
TRUNCATE TABLE report;
TRUNCATE TABLE user;
TRUNCATE TABLE report_like;
TRUNCATE TABLE report_etcetera_keyword;
TRUNCATE TABLE report_rain_keyword;
TRUNCATE TABLE report_weather_keyword;
TRUNCATE TABLE etcetera_keyword;
TRUNCATE TABLE rain_keyword;
TRUNCATE TABLE weather_keyword;


-- 외래 키 제약 조건 다시 활성화
SET FOREIGN_KEY_CHECKS = 1;