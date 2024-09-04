package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.repository.PostRepository;
import com.teamsix.firstteamproject.travelplan.service.AwsS3Service;
import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    public PostDTO savePost(PostDTO postDTO, List<MultipartFile> images) {
        if(!images.isEmpty()){
            postDTO.mappingImageNameAndUrl(awsS3Service.uploadCommunityImageList(images, postDTO.getUserId()));
        }
        // postImage리스트를 갖는 DTO의 Entity화 시키기
        Post post = postDTO.toEntity();
        // 후에 관계설정 코드 수정하기 (-> UserDTO객체 수정 필요)
        User user = userRepository.findUserById(postDTO.getUserId());
        post.setUser(user);
        if(!post.getComments().isEmpty()){
            for(Comment comment : post.getComments()){
                comment.setUser(user);
            }
        }

        return  postRepository.save(post).toDTO();
    }
}
