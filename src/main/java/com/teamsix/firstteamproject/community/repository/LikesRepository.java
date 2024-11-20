package com.teamsix.firstteamproject.community.repository;

import com.teamsix.firstteamproject.community.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    public Optional<Likes> findByPostIdAndUserId(Long postId, Long userId);

}
