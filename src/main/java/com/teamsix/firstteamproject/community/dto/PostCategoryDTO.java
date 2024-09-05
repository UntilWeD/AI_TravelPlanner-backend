package com.teamsix.firstteamproject.community.dto;

import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.entity.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostCategoryDTO {
    private Long id;
    private String name;
    private String description;

    public PostCategory toEntity(){
        return PostCategory.builder()
                .id(getId())
                .name(getName())
                .description(getDescription())
                .posts(new ArrayList<Post>())
                .build();
    }

}
