package com.teamsix.firstteamproject.community.repository;

import com.teamsix.firstteamproject.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByCreatedAt(Long postId);

    void deleteByIdAndPost_Id(Long id, Long postId);

}
