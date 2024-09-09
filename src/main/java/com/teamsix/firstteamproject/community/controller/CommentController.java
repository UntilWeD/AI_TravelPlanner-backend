package com.teamsix.firstteamproject.community.controller;

import com.teamsix.firstteamproject.community.dto.CommentDTO;
import com.teamsix.firstteamproject.community.dto.PostDTO;
import com.teamsix.firstteamproject.community.service.CommentService;
import com.teamsix.firstteamproject.global.dto.ResultDTO;
import com.teamsix.firstteamproject.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment API", description = "Comment 관련 작업을 처리하는 API")
@RequestMapping("/board/lists/{postId}")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //comment 저장
    // frontEnd와 상의 후 comment 작성 후 결과값 반환 변경
    @Operation(summary = "Comment 저장", description = "해당 Id에 해당하는 Post에 Comment 저장")
    @PostMapping("/comments/writes")
    public ResultDTO<PostDTO> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDTO commentDTO
            ){
        return ApiUtils.ok(commentService.saveComment(postId, commentDTO));
    }


    //comment 전부 조회



    //comment 삭제



    //comment 수정


}
