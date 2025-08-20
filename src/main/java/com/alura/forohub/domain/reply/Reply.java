package com.alura.forohub.domain.reply;

import java.time.LocalDateTime;

import com.alura.forohub.domain.topic.Topic;
import com.alura.forohub.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "replies")
@Entity(name = "Reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-replies")
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @NotNull
    private String msg;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-replies")
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    public Reply(User user, Topic topic, ReplyRequestDto dto) {
        this.author = user;
        this.msg = dto.msg();
        this.topic = topic;
    }
    
}