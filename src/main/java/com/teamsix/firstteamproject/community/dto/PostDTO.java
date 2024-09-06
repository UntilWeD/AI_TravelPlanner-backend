package com.teamsix.firstteamproject.community.dto;


import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.entity.PostCategory;
import com.teamsix.firstteamproject.community.entity.PostImage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    // 후에 userId와 username userDTO로 묶기

    @NotNull
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private int likes;

    private int views;

    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
    private PostCategoryDTO postCategoryDTO;
    private List<PostImageDTO> postImageDTOS = new ArrayList<>();
    private List<CommentDTO> commentDTOS = new ArrayList<>();

    // toEntity
    public Post toEntity(){
        Post post = Post.builder()
                .username(getUsername())
                .title(getTitle())
                .content(getContent())
                .likes(getLikes())
                .views(getViews())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();

        // PostImage 엔티티화 및 연관관계 설정
        if(postImageDTOS != null){
            List<PostImage> postImages = getPostImageDTOS().stream()
                    .map(dto -> {
                        PostImage postImage = dto.toEntity();
                        postImage.setPost(post);  // 양방향 연관관계 설정
                        return postImage;
                    })
                    .collect(Collectors.toList());

            post.setPostImages(postImages);
        }

        // Comment 엔티티화 및 연관관계 설정
        if(commentDTOS != null ){
            List<Comment> comments = getCommentDTOS().stream()
                    .map(dto -> {
                        Comment comment = dto.toEntity();
                        comment.setPost(post);  // 양방향 연관관계 설정
                        return comment;
                    })
                    .collect(Collectors.toList());

            post.setComments(comments);
        }

        if(postCategoryDTO != null){
            // PostCategory 엔티티화 및 연관관계 설정
            PostCategory postCategory = postCategoryDTO.toEntity();
            postCategory.addPost(post); // 양방향 연관관계 설정


            post.setPostCategory(postCategory);
        }

        return post;
    }




    // awsS3서비스에서 받은 imageUrls를 postImageDTOS에 추가
    public void mappingImageNameAndUrl(List<String> imageUrls) {
        for (String imageUrl :imageUrls){
            postImageDTOS.add(
                    PostImageDTO.builder()
                            .imageName(extractFileName(imageUrl))
                            .imageUrl(imageUrl)
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
