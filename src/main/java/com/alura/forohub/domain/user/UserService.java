package com.alura.forohub.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.forohub.infrastructure.exception.ValidationException;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto read(Long id) {
        return new UserResponseDto(userRepository.getReferenceById(id));
    }

    @Transactional
    public UserResponseDto update(Long userId, UserRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getUsername() != dto.username()) {
            throw new ValidationException("ERROR: User name can't be changed.");
        }
        user.setMail(dto.mail());
        UserResponseDto newDto = new UserResponseDto(userRepository.save(user));
        return newDto;
    }
    
}
