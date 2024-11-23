package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.dto.SimplePostDTO;
import com.teamsix.firstteamproject.community.entity.Comment;
import com.teamsix.firstteamproject.community.entity.Likes;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.entity.PostImage;
import com.teamsix.firstteamproject.community.repository.LikesRepository;
import com.teamsix.firstteamproject.community.repository.PostRepository;
import com.teamsix.firstteamproject.travelplan.service.AwsS3Service;
import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final AwsS3Service awsS3Service;


    public PostDTO savePost(PostDTO postDTO, List<MultipartFile> images) {
        if(images != null){
            postDTO.mappingImageNameAndUrl(awsS3Service.uploadCommunityImageList(images, postDTO.getUserId()));
        }

        // postImage리스트를 갖는 DTO의 Entity화 시키기
        Post post = postDTO.toEntity();
        // 후에 관계설정 코드 수정하기 (-> UserDTO객체 수정 필요)
        User user = userRepository.findUserById(postDTO.getUserId()).get();
        post.setUser(user);
        if(post.getComments() != null ){
            for(Comment comment : post.getComments()){
                comment.setUser(user);
            }
        }

        log.info("Saving post : {} ", post.toString());
        return  postRepository.save(post).toDTO();
    }

    // 페이징을 사용하여 page화하여 반환한다.
    public Page<SimplePostDTO> getSimplePostDTOS(Pageable pageable, Long categoryId){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        int size = pageable.getPageSize();
        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return postRepository.findAllByPostCategoryIdOrderByCreatedAtDesc(categoryId, pageable)
                .map(Post::toSimpleDTO);

//        이전에 리스트로만 출력하던 코드
//        return  postRepository.findAllByPostCategoryIdOrderByCreatedAtDesc(categoryId)
//                .stream().map(Post::toSimpleDTO).collect(Collectors.toList());
    }

    public PostDTO getPostDTO(Long postId){
        Post findingPost = postRepository.findById(postId).get();
        findingPost.addViews();
        return findingPost.toDTO();
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

    /**
     * Post 수정 메서드
     * 1) 내용 수정
     * 2) 이미지 변경 여부 확인 및 삭제
     * 3) 추가된 이미지 저장과 엔티티 이미지리스트에 추가
     * @param images
     * @param postDTO
     * @return
     */
    public PostDTO updatePost(List<MultipartFile> images, PostDTO postDTO){
        // 1) 내용 변경 적용
        Post post = postRepository.findById(postDTO.getId()).get();
        post.updatePostFromDTO(postDTO);

        // 2) 삭제된 이미지 수정 적용
        if(post.getPostImages().size() != postDTO.getPostImageDTOS().size()){
            List<String> deletingImageNames = post.getPostImageNames();
            List<String> savingImageNames = postDTO.getPostImageNames();

            for(int i = deletingImageNames.size() -1 ; i>= 0; i--){
                if(savingImageNames.contains(deletingImageNames.get(i))){
                    deletingImageNames.remove(i);
                }
            }

            if(!deletingImageNames.isEmpty()){
                //엔티티상 이미지 삭제
                post.removePostImage(deletingImageNames);

                //저장소상 이미지 삭제
                awsS3Service.deleteCommunityImages(deletingImageNames, post.getUser().getId());
            }
        }

        // 3) 추가된 이미지 적용
        if(images != null){
            post.mappingImageNameAndUrl(awsS3Service.uploadCommunityImageList(images, post.getUser().getId()));
        }


        return post.toDTO();
    }

    // (사용자당 하나의 좋아요 제한 로직 코드 작성하기 및 테이블 수정)
    public PostDTO addingLikesToPost(Long postId, Long userId){
        log.info(likesRepository.findByPostIdAndUserId(userId, postId).toString());
        if(likesRepository.findByPostIdAndUserId(userId, postId).isPresent()){
            throw new RuntimeException("이미 좋아요를 누른 게시글입니다.");
        }

        Likes likes = Likes.builder()
                .postId(postId)
                .userId(userId)
                .build();

        likesRepository.save(likes);
        Post post = postRepository.findById(postId).get();

        return post.toDTO();
    }


}
