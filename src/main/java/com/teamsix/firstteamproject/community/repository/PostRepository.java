package com.teamsix.firstteamproject.community.repository;

import com.teamsix.firstteamproject.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    public Optional<Post> findById(Long postId);
    public Page<Post> findAllByPostCategoryIdOrderByCreatedAtDesc(Long postCategoryId, Pageable pageable);

//    public List<Post> findAllByPostCategoryIdOrderByCreatedAtDesc(Long postCategoryId);
}

