package com.alura.forohub.controller;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.net.URI;

import com.alura.forohub.domain.topic.*;
import com.alura.forohub.domain.user.User;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/topics")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.read(id));
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseDto>> getAll(@PageableDefault(size = 20, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(topicService.readAll(pageable));
    }

    @PostMapping()
    public ResponseEntity<TopicResponseDto> post(@RequestBody @Valid TopicRequestDto dto, @AuthenticationPrincipal UserDetails userDetails, UriComponentsBuilder uriBuilder) {
        Long userId = ((User) userDetails).getId();
        TopicResponseDto newTopicDto = topicService.create(userId, dto);
        URI url = uriBuilder.path("/topics/{topicId}").buildAndExpand(newTopicDto.id()).toUri();
        return ResponseEntity.created(url).body(newTopicDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDto> put(@PathVariable Long id, @RequestBody @Valid TopicRequestDto request, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        TopicResponseDto newTopicDto = topicService.update(id, request, userId);
        return ResponseEntity.ok(newTopicDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        topicService.setStatusFalse(id, userId);
        return ResponseEntity.noContent().build();
    }
}