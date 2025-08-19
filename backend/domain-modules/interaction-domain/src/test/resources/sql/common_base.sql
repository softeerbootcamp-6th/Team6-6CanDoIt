-- ===== GRID =====
INSERT INTO grid(id, x, y) VALUES (1000000, 94, 117);

-- ===== IMAGE =====
INSERT INTO image(id, image_url, created_at) VALUES (1001, 'https://img/report-1.jpg', NOW());
INSERT INTO image(id, image_url, created_at) VALUES (1002, 'https://img/report-2.jpg', NOW());
INSERT INTO image(id, image_url, created_at) VALUES (2001, 'https://img/user-1.jpg',   NOW());
INSERT INTO image(id, image_url, created_at) VALUES (2002, 'https://img/user-2.jpg',   NOW());
INSERT INTO image(id, image_url, created_at) VALUES (3001, 'https://img/report-3.jpg', NOW());
INSERT INTO image(id, image_url, created_at) VALUES (3002, 'https://img/report-4.jpg', NOW());
INSERT INTO image(id, image_url, created_at) VALUES (4001, 'https://img/user-3.jpg',   NOW());
INSERT INTO image(id, image_url, created_at) VALUES (4002, 'https://img/user-4.jpg',   NOW());
INSERT INTO image(id, image_url, created_at) VALUES (5001, 'https://img/mountain.jpg', NOW());
INSERT INTO image(id, image_url, created_at) VALUES (6001, 'https://img/course.jpg',   NOW());

-- ===== MOUNTAIN =====
INSERT INTO mountain(id, code, name, altitude, description, grid_id, image_id)
VALUES (1, 8888, 'Test Mountain', 1550, '테스트 산', 1000000, 5001);

-- ===== COURSE =====
INSERT INTO course(id, total_distance, altitude, name, level, total_duration, mountain_id, image_id)
VALUES (10, 5000, 300, 'Test Course', 'EASY', 120, 1, 6001);

-- ===== USER =====
INSERT INTO user(id, nickname, password, image_id, login_id, role)
VALUES (501, 'alice', 'nooppw', 2001, 'alice', 'NORMAL');
INSERT INTO user(id, nickname, password, image_id, login_id, role)
VALUES (502, 'bob',   'nooppw', 2002, 'bob',   'NORMAL');
INSERT INTO user(id, nickname, password, image_id, login_id, role)
VALUES (601, 'cindy', 'nooppw', 4001, 'cindy', 'NORMAL');
INSERT INTO user(id, nickname, password, image_id, login_id, role)
VALUES (602, 'dave',  'nooppw', 4002, 'dave',  'NORMAL');

-- ===== WEATHER KEYWORD (마스터) =====
INSERT INTO weather_keyword(id, keyword) VALUES (9001, 'SUNNY');
INSERT INTO weather_keyword(id, keyword) VALUES (9002, 'CLOUDY');

-- ===== RAIN KEYWORD (마스터) =====
INSERT INTO rain_keyword(id, keyword) VALUES (9101, 'LIGHT');
INSERT INTO rain_keyword(id, keyword) VALUES (9102, 'HEAVY');

-- ===== ETCETRA KEYWORD (마스터) =====
INSERT INTO etcetera_keyword(id, keyword) VALUES (9201, 'FOGGY');
INSERT INTO etcetera_keyword(id, keyword) VALUES (9202, 'DUSTY');
