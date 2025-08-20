package com.alura.forohub.domain.topic;

import jakarta.validation.constraints.NotNull;

public record TopicRequestDto(
    @NotNull
    String course,
    @NotNull
    String title,
    @NotNull
    String msg
) {
    
}