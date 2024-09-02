package com.teamsix.firstteamproject.community.entity;

import com.teamsix.firstteamproject.user.entity.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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
    private Category category;

    @Column(name = "username")
    private String username;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostImage> postImage;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column(name = "like")
    private int like;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;




}
