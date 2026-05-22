package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.IdResponse;
import org.sopt.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "좋아요 추가", description = "게시글에 좋아요를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 추가 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 또는 사용자 없음", content = @Content),
            @ApiResponse(responseCode = "409", description = "이미 좋아요를 누른 게시글", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BaseResponse<IdResponse>> addLike(
            @Parameter(description = "게시글 ID", required = true, example = "1")
            @PathVariable Long postId,
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(likeService.addLike(postId, getAuthenticatedUserId(authentication))));
    }

    @Operation(summary = "좋아요 취소", description = "게시글에 누른 좋아요를 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "404", description = "좋아요 내역 없음", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> cancelLike(
            @Parameter(description = "게시글 ID", required = true, example = "1")
            @PathVariable Long postId,
            Authentication authentication
    ) {
        likeService.cancelLike(postId, getAuthenticatedUserId(authentication));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Long getAuthenticatedUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
