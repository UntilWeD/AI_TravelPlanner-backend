package com.teamsix.firstteamproject.user.email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, String> {
    Optional<EmailToken> findByIdAndExpirationDateAfterAndExpired(String emailTokenId, LocalDateTime now, boolean expired);

    void deleteEmailTokenById(String emailTokenId);
}
