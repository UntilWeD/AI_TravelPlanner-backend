package com.teamsix.firstteamproject.community.dto;

import com.teamsix.firstteamproject.community.entity.PostImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImageDTO {

    private Long id;
    private String imageName;
    private String imageUrl;

    public PostImage toEntity(){
        return PostImage.builder()
                .imageName(getImageName())
                .imageUrl(getImageUrl())
                .build();
    }

}
