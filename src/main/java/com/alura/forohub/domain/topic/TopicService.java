package com.alura.forohub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.alura.forohub.domain.user.User;
import com.alura.forohub.domain.user.UserRepository;
import com.alura.forohub.infrastructure.exception.ValidationException;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;

@AllArgsConstructor
@Service
public class TopicService {
    
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public TopicResponseDto create(Long userId, TopicRequestDto dto) {
        User userExists = userExists(userId);
        Topic newTopic = new Topic(userExists, dto);
        TopicResponseDto newDto = new TopicResponseDto(topicRepository.save(newTopic));
        return newDto;
    }

    public TopicResponseDto read(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
        return new TopicResponseDto(topic);
    }

    public Page<TopicResponseDto> readAll(Pageable pageable) {
        Page<Topic> page = topicRepository.findAll(pageable);
        Page<TopicResponseDto> dtoPage = page.map(topic -> new TopicResponseDto(topic));
        return dtoPage;
    }

    @Transactional
    public TopicResponseDto update(Long topicId, TopicRequestDto dto, Long userId) {
        User userExists = userExists(userId);
        Topic topicExists = topicExists(topicId);
        if (topicExists.getAuthor() != userExists) {
            throw new ValidationException("ERROR: Current User is not the author.");
        }
        if (topicExists.getCourse() != dto.course()) {
            throw new ValidationException("ERROR: Topic Category can't be changed.");
        }
        if (topicExists.getTitle() != dto.title()) {
            throw new ValidationException("ERROR: Topic Title can't be changed.");
        }
        topicExists.setMsg(dto.msg());
        TopicResponseDto newDto = new TopicResponseDto(topicRepository.save(topicExists));
        return newDto;
    }

    @Transactional
    public void setStatusFalse(Long id, Long userId) {
        User userExists = userExists(userId);
        Topic topicExists = topicExists(id);
        if (topicExists.getAuthor() != userExists) {
            throw new ValidationException("ERROR: Current User is not the author.");
        }
        topicExists.setStatus(false);
        topicRepository.save(topicExists);
    }

    private User userExists(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private Topic topicExists(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
    }
}
