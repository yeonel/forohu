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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.alura.forohub.domain.reply.*;
import com.alura.forohub.domain.user.User;

@RestController
@RequestMapping("/replies")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/{id}")
    public ResponseEntity<ReplyResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(replyService.read(id));
    }

    @GetMapping()
    public ResponseEntity<Page<ReplyResponseDto>> getAll(@PageableDefault(size = 20, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(replyService.readAll(pageable));
    }

    @PostMapping()
    public ResponseEntity<ReplyResponseDto> post(@RequestBody @Valid ReplyRequestDto dto, @AuthenticationPrincipal UserDetails userDetails, UriComponentsBuilder uriBuilder) {
        Long userId = ((User) userDetails).getId();
        ReplyResponseDto newReplyDto = replyService.create(userId, dto);
        URI url = uriBuilder.path("/replies/{replyId}").buildAndExpand(newReplyDto.id()).toUri();
        return ResponseEntity.created(url).body(newReplyDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplyResponseDto> put(@PathVariable Long id, @RequestBody @Valid ReplyRequestDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        ReplyResponseDto newReplyDto = replyService.update(id, dto, userId);
        return ResponseEntity.ok(newReplyDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        replyService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
    
}
