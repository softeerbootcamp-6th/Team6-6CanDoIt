/* 1. grid ----------------------------------------------------------------- */
INSERT INTO grid (id, x, y)
VALUES (1, 60, 120),
       (2, 61, 121);

/* 2. image ---------------------------------------------------------------- */
INSERT INTO image (id, image_url, created_at)
VALUES (10, 'https://cdn.example.com/img/mountain_seorak.jpg', NOW()),
       (11, 'https://cdn.example.com/img/course_dinosaur.jpg', NOW()),
       (12, 'https://cdn.example.com/img/course_bisandae.jpg', NOW());

/* 3. mountain ------------------------------------------------------------- */
INSERT INTO mountain (id, code, name, altitude, description, grid_id, image_id)
VALUES (7, 1001, '설악산', 1708,
        '한국의 대표 명산으로 사계절 절경이 아름다운 산',
        1, 10);

/* 4. sun_time ------------------------------------------------------------- */
INSERT INTO sun_time (id, sunrise, sunset, date, mountain_id)
VALUES (100, '05:30:00', '19:30:00', '2025-08-15', 7),
       (101, '05:31:00', '19:29:00', '2025-08-16', 7);

/* 5. course --------------------------------------------------------------- */
INSERT INTO course (id, total_distance, altitude, name, level, total_duration,
                    mountain_id, image_id)
VALUES (1, 12.3, 480, '공룡능선', 'HARD', 480, 7, 11),
       (2, 8.2, 300, '비선대 코스', 'MEDIUM', 300, 7, 12);

/* 6. course_point --------------------------------------------------------- */
INSERT INTO course_point (id, longitude, latitude, course_id, grid_id)
VALUES (1000, 128.4567, 38.1234, 1, 1), -- 공룡능선 포인트들
       (1001, 128.4578, 38.1240, 1, 2),
       (1002, 128.4589, 38.1250, 1, 1),

       (2000, 128.4600, 38.1200, 2, 1), -- 비선대 코스 포인트들
       (2001, 128.4610, 38.1210, 2, 2);