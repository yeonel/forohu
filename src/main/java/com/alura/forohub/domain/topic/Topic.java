package com.alura.forohub.domain.topic;

import java.time.LocalDateTime;
import java.util.*;

import com.alura.forohub.domain.reply.Reply;
import com.alura.forohub.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference // Prevents recursion by ignoring the 'user' field during serialization.
    @JoinColumn(name = "user_id", nullable=false)
    private User author;
    
    @NotNull
    private String title;
    
    @NotNull
    private String msg;

    @NotNull
    private String course;

    private Boolean status = true;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("topic-replies")
    private List<Reply> replies = new ArrayList<>();


    public Topic(User user, TopicRequestDto dto) {
        this.author = user;
        this.course = dto.course();
        this.msg = dto.msg();
        this.title = dto.title();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return title != null && title.equalsIgnoreCase(topic.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title != null ? title.toLowerCase() : null);
    }
}
