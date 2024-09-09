package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.CommentDTO;
import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.repository.CommentRepository;
import com.teamsix.firstteamproject.community.repository.PostRepository;
import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findUserById(commentDTO.getUserId());

        comment.setUser(user);
        comment.setPost(post);

        return post.toDTO();
    }
}
