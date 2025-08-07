CREATE TABLE grid
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    x  INT    NOT NULL,
    y  INT    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE image
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_url  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mountain
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    code        INT           NOT NULL,
    name        VARCHAR(255)  NOT NULL,
    altitude    INT           NOT NULL,
    description VARCHAR(1000) NOT NULL,
    grid_id     BIGINT        NOT NULL,
    image_id    BIGINT        NOT NULL,
    CONSTRAINT fk_mountain_grid_id
        FOREIGN KEY (grid_id) REFERENCES grid (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_mountain_image_id
        FOREIGN KEY (image_id) REFERENCES image (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE sun_time
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    sunrise     TIME   NOT NULL,
    sunset      TIME   NOT NULL,
    date        DATE   NOT NULL,
    mountain_id BIGINT NOT NULL,
    CONSTRAINT fk_sun_time_mountain_id
        FOREIGN KEY (mountain_id) REFERENCES mountain (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_sun_time_mountain_date UNIQUE (mountain_id, date)
);

CREATE TABLE course
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    total_distance DOUBLE NOT NULL,
    altitude       INT          NOT NULL,
    name           VARCHAR(255) NOT NULL,
    level          VARCHAR(10)  NOT NULL, -- ENUM이 없으므로 VARCHAR로 변경
    total_duration INT          NOT NULL,
    mountain_id    BIGINT       NOT NULL,
    image_id       BIGINT       NOT NULL,
    CONSTRAINT fk_course_mountain_id
        FOREIGN KEY (mountain_id) REFERENCES mountain (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_course_image_id
        FOREIGN KEY (image_id) REFERENCES image (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE course_point
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    longitude DOUBLE NOT NULL,
    latitude DOUBLE NOT NULL,
    course_id BIGINT NOT NULL,
    grid_id   BIGINT NOT NULL,
    CONSTRAINT fk_course_point_course_id
        FOREIGN KEY (course_id) REFERENCES course (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_course_point_grid_id
        FOREIGN KEY (grid_id) REFERENCES grid (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);
