package com.teamsix.firstteamproject.user.email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {
    public void sendEmail(SimpleMailMessage email);
}
