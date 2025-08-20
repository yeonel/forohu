CREATE TABLE Replies (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    msg TEXT NOT NULL,
    createdAt DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_replies_user FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    CONSTRAINT fk_replies_topic FOREIGN KEY (topic_id) REFERENCES Topics (id) ON DELETE CASCADE
);