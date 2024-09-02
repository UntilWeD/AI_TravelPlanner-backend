package com.teamsix.firstteamproject.community.entity;

import jakarta.persistence.*;

@Table(name = "post_image")
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url")
    private String imageUrl;


}
