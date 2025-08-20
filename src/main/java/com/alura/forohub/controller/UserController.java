package com.alura.forohub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.alura.forohub.domain.user.*;

@RestController
@RequestMapping("/users")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.read(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> put(@PathVariable Long id, @RequestBody @Valid UserRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        UserResponseDto newUserDto = userService.update(userId, dto);
        return ResponseEntity.ok(newUserDto);
    }
}
