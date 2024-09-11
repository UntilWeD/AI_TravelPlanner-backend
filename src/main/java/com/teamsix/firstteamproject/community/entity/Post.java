package com.teamsix.firstteamproject.community.entity;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.dto.SimplePostDTO;
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

    @Column(name = "likes")
    private int likes;

    @Column(name = "views")
    private int views;

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

    public void removePostImage(List<String> removingImages){
        for(int i = postImages.size() -1 ; i>= 0; i--){
            if(removingImages.contains(postImages.get(i).getImageName())){
                postImages.remove(i);
            }
        }
    }

    // awsS3서비스에서 받은 imageUrls를 postImageDTOS에 추가
    public void mappingImageNameAndUrl(List<String> imageUrls) {
        for (String imageUrl :imageUrls){
            postImages.add(
                    PostImage.builder()
                            .post(this)
                            .imageName(extractFileName(imageUrl))
                            .imageUrl(imageUrl)
                            .build()
            );
        }
    }

    public List<String> getPostImageNames(){
        return postImages.stream().map(
                PostImage::getImageName
        ).collect(Collectors.toList());
    }

    public void updatePostFromDTO(PostDTO dto){
        postCategory = dto.getPostCategoryDTO().toEntity();
        title = dto.getTitle();
        content = dto.getContent();
        updatedAt = dto.getUpdatedAt();

    }


    public PostDTO toDTO(){
        return PostDTO.builder()
                .id(getId())
                .userId(getUser().getId())
                .username(getUsername())
                .title(getTitle())
                .content(getContent())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .likes(getLikes())
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

    public SimplePostDTO toSimpleDTO(){
        return SimplePostDTO.builder()
                .id(getId())
                .username(getUsername())
                .title(getTitle())
                .likes(getLikes())
                .createdAt(getCreatedAt())
                .build();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", postCategory=" + postCategory +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postImages=" + postImages +
                ", comments=" + comments +
                ", likes=" + likes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public void addViews(){
        views++;
    }

    public void addLikes(){ likes++;}

    public void addComments(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }

    private String extractOriginalFileName(String url){
        return url.substring(url.lastIndexOf('-') + 1, url.lastIndexOf("."));
    }

    private String extractFileName(String url){
        return url.substring(url.lastIndexOf('/') + 1);
    }

}
