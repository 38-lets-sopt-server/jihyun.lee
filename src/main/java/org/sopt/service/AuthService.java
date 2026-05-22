package org.sopt.service;

import org.sopt.domain.RefreshToken;
import org.sopt.domain.User;
import org.sopt.dto.response.TokenResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.exception.AuthErrorCode;
import org.sopt.exception.CustomException;
import org.sopt.exception.UserErrorCode;
import org.sopt.repository.RefreshTokenRepository;
import org.sopt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    public AuthService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public UserResponse loginWithCredentials(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_CREDENTIALS));

        if (!user.getPassword().equals(password)) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        return new UserResponse(user);
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        UserResponse member = loginWithCredentials(email, password);

        String accessToken = jwtService.generateAccessToken(member.id(), member.email());
        String refreshToken = jwtService.generateRefreshToken(member.id());

        // 기존 Refresh Token 삭제 후 새로 저장
        refreshTokenRepository.deleteByMemberId(member.id());
        refreshTokenRepository.save(
                RefreshToken.of(member.id(), refreshToken, refreshTokenExpiresInSeconds)
        );

        return TokenResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        Long memberId = verifyRefreshToken(refreshToken);
        RefreshToken savedRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN));

        if (!savedRefreshToken.getMemberId().equals(memberId) || savedRefreshToken.isExpired()) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String newRefreshToken = jwtService.generateRefreshToken(user.getId());
        savedRefreshToken.rotate(newRefreshToken, refreshTokenExpiresInSeconds);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    private Long verifyRefreshToken(String refreshToken) {
        try {
            return jwtService.verifyAndGetUserId(refreshToken);
        } catch (CustomException e) {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        return new UserResponse(user);
    }
}
