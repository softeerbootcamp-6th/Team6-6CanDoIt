-- REPORT (id DESC: 202 > 201), type은 스키마의 enum('SAFE','WEATHER')
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (201, 'WEATHER', 'r201', 7,  NOW(), 501, 10, 1001);

INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (202, 'WEATHER', 'r202', 5,  NOW(), 502, 10, 1002);

-- 키워드 연결(선택): 201에만 키워드 부여
INSERT INTO report_weather_keyword(report_id, weather_keyword_id) VALUES (201, 9001); -- SUNNY
INSERT INTO report_rain_keyword(report_id, rain_keyword_id)       VALUES (201, 9101); -- LIGHT
INSERT INTO report_etcetera_keyword(report_id, etcetera_keyword_id) VALUES (201, 9201); -- FOGGY
