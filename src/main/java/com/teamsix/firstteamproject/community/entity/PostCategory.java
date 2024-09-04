package com.teamsix.firstteamproject.community.entity;

import com.teamsix.firstteamproject.community.dto.PostCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "category")
@Entity
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "postCategory")
    private List<Post> posts;

    public PostCategoryDTO toDTO(){
        return PostCategoryDTO.builder()
                .id(getId())
                .name(getName())
                .description(getDescription())
                .build();
    }

    public void addPost(Post post){
        posts.add(post);
    }




}
