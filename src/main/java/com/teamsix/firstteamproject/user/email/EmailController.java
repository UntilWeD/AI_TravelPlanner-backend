package com.teamsix.firstteamproject.user.email;

import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailController {
    public ResponseEntity viewConfirmEmail(@Valid @RequestParam String token);
    public ResponseEntity resendEmail(@RequestParam String email);
    public ResponseEntity sendEmail(@RequestBody String email);
}
