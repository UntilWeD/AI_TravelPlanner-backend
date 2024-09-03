package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.repository.PostRepository;
import com.teamsix.firstteamproject.travelplan.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AwsS3Service awsS3Service;

    public PostDTO savePost(PostDTO postDTO, List<MultipartFile> images) {
        if(!images.isEmpty()){
            postDTO.mappingImageNameAndUrl(awsS3Service.uploadCommunityImageList(images, postDTO.getUserId()));
        }

//        Post savedPost = postRepository.save();

        return PostDTO.builder().build();
    }
}
