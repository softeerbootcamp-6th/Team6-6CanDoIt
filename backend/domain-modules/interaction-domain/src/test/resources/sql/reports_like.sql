-- like=10: id 302 > 301
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (301, 'WEATHER', 'r301', 10, NOW(), 601, 10, 3001);
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (302, 'WEATHER', 'r302', 10, NOW(), 602, 10, 3002);

-- like=8: id 304 > 303
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (303, 'WEATHER', 'r303', 8, NOW(), 601, 10, 3001);
INSERT INTO report(id, type, content, like_count, created_at, user_id, course_id, image_id)
VALUES (304, 'WEATHER', 'r304', 8, NOW(), 602, 10, 3002);
