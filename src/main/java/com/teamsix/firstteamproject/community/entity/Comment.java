package com.teamsix.firstteamproject.community.entity;

import com.teamsix.firstteamproject.community.dto.CommentDTO;
import com.teamsix.firstteamproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public CommentDTO toDTO(){
        return CommentDTO.builder()
                .id(id)
                .userId(getUser().getId())
                .userName(getUser().getName())
                .content(getContent())
                .updatedAt(getUpdatedAt())
                .createdAt(getCreatedAt())
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
