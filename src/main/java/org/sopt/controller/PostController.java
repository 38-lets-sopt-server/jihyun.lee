package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.*;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 생성", description = "새 게시글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패 (제목/내용 누락, 제목 길이 초과, 게시판 종류 오류)", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BaseResponse<IdResponse>> createPost(
            @RequestBody CreatePostRequest request
    ) {
        IdResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @Operation(summary = "게시글 전체 조회", description = "게시판 종류별 게시글 목록을 페이지네이션으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (boardType 누락/오류, page/size 범위 초과)", content = @Content)
    })
    @GetMapping
    public BaseResponse<PostListResponse> getAllPosts(
            @Parameter(description = "게시판 종류 (FREE, HOT, SECRET)", required = true, example = "FREE")
            @RequestParam String boardType,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기 (1~100)", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return BaseResponse.success(postService.getAllPosts(boardType, page, size));
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음", content = @Content)
    })
    @GetMapping("/{id}")
    public BaseResponse<PostResponse> getPost(
            @Parameter(description = "게시글 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        return BaseResponse.success(postService.getPost(id));
    }

    @Operation(summary = "게시글 수정", description = "게시글 제목과 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패 (제목/내용 누락, 제목 길이 초과)", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글 없음", content = @Content)
    })
    @PutMapping("/{id}")
    public BaseResponse<IdResponse> updatePost(
            @Parameter(description = "게시글 ID", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request
    ) {
        return BaseResponse.success(postService.updatePost(id, request));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 ID로 특정 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
