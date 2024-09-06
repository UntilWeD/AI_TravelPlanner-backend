package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.dto.SimplePostDTO;
import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.entity.PostImage;
import com.teamsix.firstteamproject.community.repository.PostRepository;
import com.teamsix.firstteamproject.travelplan.service.AwsS3Service;
import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    public PostDTO savePost(PostDTO postDTO, List<MultipartFile> images) {
        if(images != null){
            postDTO.mappingImageNameAndUrl(awsS3Service.uploadCommunityImageList(images, postDTO.getUserId()));
        }

        // postImage리스트를 갖는 DTO의 Entity화 시키기
        Post post = postDTO.toEntity();
        // 후에 관계설정 코드 수정하기 (-> UserDTO객체 수정 필요)
        User user = userRepository.findUserById(postDTO.getUserId());
        post.setUser(user);
        if(post.getComments() != null ){
            for(Comment comment : post.getComments()){
                comment.setUser(user);
            }
        }

        log.info("Saving post : {} ", post.toString());
        return  postRepository.save(post).toDTO();
    }

    public List<SimplePostDTO> getSimplePostDTOS(Long categoryId){
        return  postRepository.findAllByPostCategoryIdOrderByCreatedAtDesc(categoryId)
                .stream().map(Post::toSimpleDTO).collect(Collectors.toList());
    }

    public PostDTO getPostDTO(Long postId){
        return postRepository.findById(postId).get().toDTO();
    }

    public void deletePost(Long postId){
        Post deletingPost = postRepository.findById(postId).get();

        try{
            if(deletingPost.getPostImages() != null){
                awsS3Service.deleteCommunityImages(deletingPost.getPostImages()
                                .stream().map(PostImage::getImageName).collect(Collectors.toList()),
                        deletingPost.getUser().getId());
            }
            postRepository.deleteById(postId);
        } catch (Exception ex){
            throw new RuntimeException("While Removing Image, Exception Occured!!");
        }
    }


}
