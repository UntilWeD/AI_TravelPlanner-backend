package com.teamsix.firstteamproject.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImageDTO {

    private String imageName;
    private String imageUrl;
}
