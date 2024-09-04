package com.teamsix.firstteamproject.community.entity;

import com.teamsix.firstteamproject.community.dto.PostImageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post_image")
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_url")
    private String imageUrl;

    public void setPost(Post post){
        this.post = post;
    }

    public PostImageDTO toDTO(){
        return PostImageDTO.builder()
                .id(getId())
                .imageName(getImageName())
                .imageUrl(getImageUrl())
                .build();
    }


}
