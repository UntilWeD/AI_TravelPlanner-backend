package com.teamsix.firstteamproject.community.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private Long userId;
    private Long categoryId;
    private String username;
    private String title;
    private String content;
    private int like;
    private Date createdAt;
    private Date updatedAt;
    private List<PostImageDTO> postImageDTOS;

    //toENtity



    public void mappingImageNameAndUrl(List<String> imageUrls) {

        for (String imageUrl :imageUrls){
            postImageDTOS.add(
                    PostImageDTO.builder()
                            .imageName(extractOriginalFileName(imageUrl))
                            .imageUrl(extractFileName(imageUrl))
                            .build()
            );
        }
    }

    private String extractOriginalFileName(String url){
        return url.substring(url.lastIndexOf('-') + 1, url.lastIndexOf("."));
    }

    private String extractFileName(String url){
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
