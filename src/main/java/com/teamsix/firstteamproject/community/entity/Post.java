package com.teamsix.firstteamproject.community.entity;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.user.entity.User;
import jakarta.persistence.*;
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
@Table(name = "post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PostCategory postCategory;

    @Column(name = "username")
    private String username;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostImage> postImages;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column(name = "like")
    private int like;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public void setPostImages(List<PostImage> newPostImages) {
        this.postImages = new ArrayList<>(newPostImages != null ? newPostImages : Collections.emptyList());
    }

    public void setPostCategory(PostCategory postCategory){
        this.postCategory =postCategory;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addPostImages(List<PostImage> additionalPostImages) {
        if (additionalPostImages != null) {
            this.postImages.addAll(additionalPostImages);
        }
    }


    public PostDTO toDTO(){
        return PostDTO.builder()
                .id(getId())
                .username(getUsername())
                .title(getTitle())
                .content(getContent())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .like(getLike())
                .postCategoryDTO(Optional.ofNullable(getPostCategory())
                        .map(PostCategory::toDTO).orElse(null))
                .postImageDTOS(
                        Optional.ofNullable(getPostImages())
                                .map(images -> images.stream()
                                        .map(PostImage::toDTO)
                                        .collect(Collectors.toList()))
                                .orElse(Collections.emptyList())
                )
                .commentDTOS(
                        Optional.ofNullable(getComments())
                                .map(savingComments -> savingComments.stream()
                                        .map(Comment::toDTO)
                                        .collect(Collectors.toList()))
                                .orElse(Collections.emptyList())
                )
                .build();
    }





}
