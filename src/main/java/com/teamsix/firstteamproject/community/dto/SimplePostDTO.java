package com.teamsix.firstteamproject.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimplePostDTO {

    private Long id;
    private String username;
    private String title;
    private int likes;
    private int views;
    private Date createdAt;



}
