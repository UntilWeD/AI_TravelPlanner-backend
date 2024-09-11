package com.teamsix.firstteamproject.community.controller;

import com.teamsix.firstteamproject.community.dto.PostCategoryDTO;
import com.teamsix.firstteamproject.community.service.PostCategoryService;
import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board/category")
@RestController
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    public PostCategoryController(PostCategoryService postCategoryService) {
        this.postCategoryService = postCategoryService;
    }

    @Operation(summary = "PostCategory 저장 ", description = "PostCategory을 저장한다.")
    @PostMapping("/writes")
    public ResultDTO<PostCategoryDTO> savePostCategory(
            @RequestBody PostCategoryDTO postCategoryDTO
    ){
        return ApiUtils.ok(postCategoryService.savePostCategory(postCategoryDTO));
    }

    @Operation(summary = "PostCategory 조회", description = "PostCategory의 세부정보를 조회한다.")
    @GetMapping("/{categoryId}")
    public ResultDTO<PostCategoryDTO> getPostCategory(
            @PathVariable Long categoryId
    ){
        return ApiUtils.ok(postCategoryService.getPostCategory(categoryId));
    }

    @Operation(summary = "모든 PostCategory 조회", description = "모든 PostCategory를 List화하여 조회한다. ")
    @GetMapping("")
    public ResultDTO<List<PostCategoryDTO>> getAllPostCategory(
    ){
        return ApiUtils.ok(postCategoryService.getAllPostCategory());
    }

    @Operation(summary = "PostCategory 수정", description = "해당 id의 PostCategory를 수정한다.")
    @PostMapping ("/{categoryId}")
    public ResultDTO<PostCategoryDTO> updatePostCategory(
            @PathVariable Long categoryId,
            @RequestBody PostCategoryDTO postCategoryDTO
    ){
        return ApiUtils.ok(postCategoryService.updatePostCategory(categoryId, postCategoryDTO));
    }

    @Operation(summary = "PostCategory 삭제", description = "해당 id의 PostCategory를 삭제한다.")
    @PostMapping ("/{categoryId}")
    public ResultDTO<String> deletePostCategory(
            @PathVariable Long categoryId
    ){
        return ApiUtils.ok(postCategoryService.deletePostCategory(categoryId));
    }



}
