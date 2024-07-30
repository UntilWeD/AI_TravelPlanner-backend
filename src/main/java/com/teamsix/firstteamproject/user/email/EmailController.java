package com.teamsix.firstteamproject.user.email;

import com.teamsix.firstteamproject.global.dto.ResultDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmailController {
    public ResponseEntity<String> viewConfirmEmail(@Valid @RequestParam String token);
    public ResultDTO sendEmail(@RequestBody String email);
    public ResponseEntity findPasswordByEmail(String email, BindingResult bindingResult);
}
