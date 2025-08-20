package com.alura.forohub.domain.reply;

import jakarta.validation.constraints.NotNull;

public record ReplyRequestDto(
    @NotNull 
    Long topicId,
    @NotNull 
    String msg
) {}