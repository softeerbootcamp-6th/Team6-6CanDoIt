-- userId=501 의 리포트 2개 (id DESC: 202 > 201)
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (201, 'WEATHER', 'r201', 3, NOW(), 501, 10, 1001);

INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (202, 'WEATHER', 'r202', 4, NOW(), 501, 10, 1002);
