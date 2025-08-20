package com.alura.forohub.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserRequestDto (
    @NotNull
    String username,
    @NotNull
    String mail
) { }