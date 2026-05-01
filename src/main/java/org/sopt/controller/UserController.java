package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.dto.request.CreateUserRequest;
import org.sopt.dto.request.UpdateUserRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.IdResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "사용자 생성", description = "새 사용자를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "사용자 생성 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패 (닉네임/이메일 누락)", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BaseResponse<IdResponse>> createUser(
            @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(userService.createUser(request)));
    }

    @Operation(summary = "사용자 조회", description = "사용자 ID로 사용자 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content)
    })
    @GetMapping("/{id}")
    public BaseResponse<UserResponse> getUser(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        return BaseResponse.success(userService.getUser(id));
    }

    @Operation(summary = "사용자 수정", description = "사용자 ID로 사용자 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패 (닉네임 누락)", content = @Content),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content)
    })
    @PutMapping("/{id}")
    public BaseResponse<IdResponse> updateUser(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request
    ) {
        return BaseResponse.success(userService.updateUser(id, request));
    }

    @Operation(summary = "사용자 삭제", description = "사용자 ID로 해당 사용자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "사용자 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUser(
            @Parameter(description = "사용자 ID", required = true, example = "1")
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
