package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.CreateUserRequest;
import org.sopt.dto.request.UpdateUserRequest;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.IdResponse;
import org.sopt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

    @Operation(summary = "사용자 수정", description = "인증된 사용자의 비밀번호와 닉네임을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 수정 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content)
    })
    @PatchMapping
    public BaseResponse<IdResponse> updateUser(
            @RequestBody UpdateUserRequest request,
            Authentication authentication
    ) {
        return BaseResponse.success(userService.updateUser(request, getAuthenticatedUserId(authentication)));
    }

    @Operation(summary = "사용자 삭제", description = "인증된 사용자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "사용자 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> deleteUser(
            Authentication authentication
    ) {
        userService.deleteUser(getAuthenticatedUserId(authentication));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Long getAuthenticatedUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
