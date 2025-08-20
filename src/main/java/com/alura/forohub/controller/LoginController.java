package com.alura.forohub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.alura.forohub.domain.user.User;
import com.alura.forohub.domain.user.UserLoginRequestDto;
import com.alura.forohub.infrastructure.security.JWTTokenDto;
import com.alura.forohub.infrastructure.security.JWTTokenService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    
    private final AuthenticationManager authenticationManager;
    private final JWTTokenService tokenService;

    @PostMapping
    public ResponseEntity<JWTTokenDto> autenticarUsuario(@RequestBody @Valid UserLoginRequestDto request) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.username(),
        request.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.createToken((User) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new JWTTokenDto(JWTtoken));
    }
}
