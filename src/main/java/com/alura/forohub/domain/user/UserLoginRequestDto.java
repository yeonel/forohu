package com.alura.forohub.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDto(
    @NotNull
    String username,
    @NotNull 
    String password) {
    
}