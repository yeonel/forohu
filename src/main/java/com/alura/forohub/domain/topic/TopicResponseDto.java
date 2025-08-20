package com.alura.forohub.domain.topic;

import java.time.LocalDateTime;

public record TopicResponseDto(
    Long id, 
    String title, 
    String msg, 
    String author, 
    String course,
    LocalDateTime createdAt) {
    public TopicResponseDto(Topic topic) {
        this(
            topic.getId(), 
            topic.getTitle(), 
            topic.getMsg(), 
            topic.getAuthor().getUsername(), 
            topic.getCourse(),
            topic.getCreatedAt());
    }
}