package com.teamsix.firstteamproject.community.controller;

import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.entity.Post;
import com.teamsix.firstteamproject.community.service.PostService;
import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/board")
@RestController
public class PostController {

    public PostController(PostService postService) {
        this.postService = postService;
    }

    private final PostService postService;

    // post 저장
    // 게시글과 사용자 정보를 같이 전송할 수 있는지 프론트엔드에게 질문 or post에 userId포함되게 하기
    @Operation(summary = "커뮤니티 글 저장", description = "요청한 글을 저장한다.")
    @PostMapping("/write")
    public ResultDTO<PostDTO> getSavePostRequest(
            @RequestPart(value = "image", required = false) List<MultipartFile> images,
            @RequestPart(value = "post") PostDTO postDTO
    ){
        return ApiUtils.ok(postService.savePost(postDTO, images));
    }

    //post 조회(리스트)


    //post 세부 조회


    //post 삭제


    //post 수정




}
