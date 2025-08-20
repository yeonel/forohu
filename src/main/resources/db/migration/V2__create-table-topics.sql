CREATE TABLE Topics (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL UNIQUE,
    msg TEXT NOT NULL,
    status tinyint NOT NULL,
    createdAt DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_topics_user FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);