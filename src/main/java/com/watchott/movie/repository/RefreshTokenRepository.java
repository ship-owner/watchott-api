package com.watchott.movie.repository;

import com.watchott.movie.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * packageName    : watchott.repository
 * fileName       : RefreshTokenRepository
 * author         : shipowner
 * date           : 2023-09-26
 * description    : Token 엔티티를 관리하기 위한 Repository
 *
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Long deleteByRefreshToken(String refreshToken);

    Long deleteByUserId(String userId);
}
