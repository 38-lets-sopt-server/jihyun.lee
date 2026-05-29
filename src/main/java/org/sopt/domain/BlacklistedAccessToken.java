package org.sopt.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BlacklistedAccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    protected BlacklistedAccessToken() {
    }

    private BlacklistedAccessToken(String token, LocalDateTime expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static BlacklistedAccessToken of(String token, LocalDateTime expiresAt) {
        return new BlacklistedAccessToken(token, expiresAt);
    }
}
