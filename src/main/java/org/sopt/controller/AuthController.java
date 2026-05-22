package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.sopt.dto.response.BaseResponse;
import org.sopt.dto.response.TokenResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        TokenResponse tokens = authService.login(email, password);
        return ResponseEntity.ok(BaseResponse.success(tokens));
    }

    @Operation(summary = "토큰 재발급 (Refresh Token 검증)")
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<TokenResponse>> reissue(
            @RequestParam("refreshToken") String refreshToken
    ) {
        return ResponseEntity.ok(BaseResponse.success(authService.reissue(refreshToken)));
    }

    @Operation(summary = "로그아웃 (Refresh Token 삭제 + Access Token 블랙리스트 등록)")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            Authentication authentication,
            @Parameter(hidden = true)
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        authService.logout(
                Long.parseLong(authentication.getName()),
                extractBearerToken(authorizationHeader)
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long memberId = Long.parseLong(authentication.getName());
        UserResponse user = authService.getUserById(memberId);

        return ResponseEntity.ok(BaseResponse.success(user));
    }

    private String extractBearerToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length()).trim();
    }
}
