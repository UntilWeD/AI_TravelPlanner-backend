package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {

    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId, LocalDateTime now, boolean expired);
    void deleteEmailTokenById(String emailTokenId);
}
