package com.teamsix.firstteamproject.community.repository;

import com.teamsix.firstteamproject.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
