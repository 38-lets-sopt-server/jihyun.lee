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
import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final long accessTokenExpiresInSeconds;
    private final long refreshTokenExpiresInSeconds;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-expires-in-seconds:1800}") long accessTokenExpiresInSeconds,
            @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}") long refreshTokenExpiresInSeconds
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
        this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
    }

    public String generateAccessToken(Long userId, String email) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withClaim("email", email)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(accessTokenExpiresInSeconds)))
                .sign(algorithm);
    }

    public String generateRefreshToken(Long userId) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(refreshTokenExpiresInSeconds)))
                .sign(algorithm);
    }

    public Long verifyAndGetUserId(String token) {
        if (token == null || token.isBlank()) {
            throw new CustomException(AuthErrorCode.TOKEN_REQUIRED);
        }

        DecodedJWT jwt;
        try {
            jwt = JWT.require(algorithm).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN);
        }

        try {
            return Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException e) {
            throw new CustomException(AuthErrorCode.INVALID_TOKEN_SUBJECT);
        }
    }
}
