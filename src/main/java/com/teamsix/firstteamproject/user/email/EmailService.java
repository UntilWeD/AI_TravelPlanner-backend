package com.teamsix.firstteamproject.user.email;

public interface EmailService {
    boolean verifyEmail(String token);

    void sendUserEmail(String email);
}
