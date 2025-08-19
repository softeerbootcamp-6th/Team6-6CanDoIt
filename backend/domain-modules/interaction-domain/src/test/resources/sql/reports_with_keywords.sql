-- 두 개 리포트: 201(FOGGY 달림), 202(키워드 없음)
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (201, 'WEATHER', 'foggy', 1, NOW(), 501, 10, 1001);

INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (202, 'WEATHER', 'plain', 2, NOW(), 502, 10, 1002);

INSERT INTO report_etcetera_keyword(report_id, etcetera_keyword_id) VALUES (201, 9201); -- FOGGY
