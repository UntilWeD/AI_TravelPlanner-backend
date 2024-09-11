package com.teamsix.firstteamproject.community.controller;

import com.teamsix.firstteamproject.community.dto.CommentDTO;
import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.service.CommentService;
import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment API", description = "Comment 관련 작업을 처리하는 API")
@RequestMapping("/board/lists/{postId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //comment 저장
    // frontEnd와 상의 후 comment 작성 후 결과값 반환 변경
    @Operation(summary = "Comment 저장", description = "해당 Id에 해당하는 Post에 Comment 저장")
    @PostMapping("/writes")
    public ResultDTO<PostDTO> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDTO commentDTO
            ){
        return ApiUtils.ok(commentService.saveComment(postId, commentDTO));
    }


    //comment 전부 조회
    @Operation(summary = "Comment 조회", description = "해당 post에 해당하는 모든 Comment들을 조회한다.")
    @GetMapping("")
    public ResultDTO<List<CommentDTO>> getComments(
            @PathVariable Long postId
    ){
        return ApiUtils.ok(commentService.getComments(postId));
    }


    //comment 삭제
    @Operation(summary = "Comment 삭제", description = "해당 id에 속하는 Comment를 삭제한다.")
    @DeleteMapping("/{commentId}")
    public ResultDTO<List<CommentDTO>> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ){
        return ApiUtils.ok(commentService.deleteComment(postId, commentId));
    }


    //comment 수정
    @Operation(summary = "Comment 수정", description = "해당 id에 속하는 Comment 수정하기")
    @PostMapping("/{commentId}")
    public ResultDTO<List<CommentDTO>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentDTO commentDTO
    ){
        return ApiUtils.ok(commentService.updateComment(postId, commentId, commentDTO));
    }


}
