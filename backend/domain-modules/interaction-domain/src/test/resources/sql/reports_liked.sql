-- 보고서 2개 (301,302) 를 userId=602 가 좋아요한 것으로 세팅
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (301, 'WEATHER', 'r301', 10, NOW(), 601, 10, 3001);

INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (302, 'WEATHER', 'r302', 10, NOW(), 601, 10, 3002);

-- 좋아요 조인 테이블 (PK: report_id, user_id)
INSERT INTO report_like(report_id, user_id) VALUES (301, 602);
INSERT INTO report_like(report_id, user_id) VALUES (302, 602);
