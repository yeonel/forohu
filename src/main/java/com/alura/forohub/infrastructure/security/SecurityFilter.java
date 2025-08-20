package com.alura.forohub.infrastructure.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.alura.forohub.domain.user.UserRepository;
import com.alura.forohub.infrastructure.utility.MyLog;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTTokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var username = tokenService.getSubject(token); // extract username
            if (username != null) {
                // Token valido
                var user = userRepository.findByUsername(username);
                var authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities()); // Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
                MyLog.info("TOKEN: " + token);
                MyLog.info("USER: " + username);
            }
        }
        filterChain.doFilter(request, response);
    }
}