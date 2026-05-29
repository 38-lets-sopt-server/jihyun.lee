package org.sopt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sopt.exception.AuthErrorCode;
import org.sopt.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.access-token-expires-in-seconds:1800}")
    private long accessTokenExpiresInSeconds;

    @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}")
    private long refreshTokenExpiresInSeconds;

    public String generateAccessToken(Long userId, String email) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("email", email)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(accessTokenExpiresInSeconds)))
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(Long userId) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(refreshTokenExpiresInSeconds)))
                .sign(getAlgorithm());
    }

    public Long verifyAndGetUserId(String token) {
        if (token == null || token.isBlank()) {
            throw new CustomException(AuthErrorCode.TOKEN_REQUIRED);
        }

        DecodedJWT jwt;
        try {
            jwt = JWT.require(getAlgorithm()).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN);
        }

        try {
            return Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException e) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_SUBJECT);
        }
    }

    public LocalDateTime getExpiresAt(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.require(getAlgorithm()).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN);
        }

        return LocalDateTime.ofInstant(jwt.getExpiresAt().toInstant(), ZoneId.systemDefault());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }
}
