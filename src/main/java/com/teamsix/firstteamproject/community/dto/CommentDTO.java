package com.teamsix.firstteamproject.community.dto;

import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private Long userId;
    private String userName;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    public Comment toEntity(){
        return Comment.builder()
                .user(User.builder()
                        .id(userId)
                        .name(userName)
                        .build())
                .content(getContent())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
