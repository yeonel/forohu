package com.alura.forohub.domain.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alura.forohub.domain.topic.Topic;
import com.alura.forohub.domain.topic.TopicRepository;
import com.alura.forohub.domain.user.User;
import com.alura.forohub.domain.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReplyService {
    
    public final ReplyRepository replyRepository;
    public final TopicRepository topicRepository;
    public final UserRepository userRepository;

    @Transactional
    public ReplyResponseDto create(Long userId, ReplyRequestDto dto) {
        User userExists = userExists(userId);
        Topic topicExists = topicExists(dto.topicId());
        Reply newReply = new Reply(userExists, topicExists, dto);
        ReplyResponseDto newDto = new ReplyResponseDto(replyRepository.save(newReply));
        return newDto;
    }

    public ReplyResponseDto read(Long id) {
        return new ReplyResponseDto(replyRepository.getReferenceById(id));
    }

    public Page<ReplyResponseDto> readAll(Pageable pageable) {
        Page<Reply> page = replyRepository.findAll(pageable);
        Page<ReplyResponseDto> dtoPage = page.map(reply -> new ReplyResponseDto(reply));
        return dtoPage;
    }

    @Transactional
    public ReplyResponseDto update(Long replyId, ReplyRequestDto dto, Long userId) {
        User userExists = userExists(userId);
        Reply replyExists = replyExists(replyId);
        if (replyExists.getAuthor() != userExists) {
            throw new IllegalArgumentException("ERROR: Current User is not the author");
        }
        replyExists.setMsg(dto.msg());
        ReplyResponseDto newDto = new ReplyResponseDto(replyExists);
        return newDto;
    }

    @Transactional
    public void delete(Long id, Long userId) {
        User userExists = userExists(userId);
        Reply replyExists = replyExists(id);
        if (replyExists.getAuthor() != userExists) {
            throw new IllegalArgumentException("ERROR: Current User is not the author");
        }
        replyRepository.deleteById(id);
    }

    public User userExists(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private Reply replyExists(Long id) {
        return replyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reply not found"));
    }

    private Topic topicExists(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Topic not found"));
    }
}
