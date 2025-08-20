package com.alura.forohub.domain.user;

public record UserResponseDto (
    String username,
    String mail
){  
    public UserResponseDto(User user) {
        this(user.getUsername(), user.getMail());
    }
}