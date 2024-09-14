package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.CommentDTO;
import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.repository.CommentRepository;
import com.teamsix.firstteamproject.community.repository.PostRepository;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public PostDTO saveComment(Long postId, CommentDTO commentDTO) {
        Comment comment = commentDTO.toEntity();
        Post post = postRepository.findById(postId).get();

        post.addComments(comment);
        commentRepository.save(comment);

        return post.toDTO();
    }

    public List<CommentDTO> getComments(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAt(postId)
                .stream().map(Comment::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> deleteComment(Long postId, Long commentId) {

        // 기존 deleteById는 제대로 삭제되지 않았으나
        // 현재 deleteByIdAndPost_Id는 제대로 삭제됨.. 후에 문제 확인하기
        commentRepository.deleteByIdAndPost_Id(postId, commentId);

        return commentRepository.findAllByPostIdOrderByCreatedAt(postId)
                .stream().map(Comment::toDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.updateComment(commentDTO);


        return commentRepository.findAllByPostIdOrderByCreatedAt(postId)
                .stream().map(Comment::toDTO).collect(Collectors.toList());
    }
}
