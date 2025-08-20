package com.alura.forohub.domain.reply;

import java.time.LocalDateTime;

public record ReplyResponseDto (
    Long id,
    String msg,
    String author,
    LocalDateTime createdAt) {
        public ReplyResponseDto(Reply reply) {
            this(
                reply.getId(), 
                reply.getMsg(), 
                reply.getAuthor().getUsername(), 
                reply.getCreatedAt());
        }
    }
