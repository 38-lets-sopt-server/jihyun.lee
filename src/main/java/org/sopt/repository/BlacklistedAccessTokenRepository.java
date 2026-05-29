package org.sopt.repository;

import org.sopt.domain.BlacklistedAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedAccessTokenRepository extends JpaRepository<BlacklistedAccessToken, Long> {
    boolean existsByToken(String token);
}
