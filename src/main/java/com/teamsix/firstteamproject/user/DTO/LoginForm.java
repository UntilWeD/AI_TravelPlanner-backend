package com.teamsix.firstteamproject.user.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank
    public String id;

    @NotBlank
    public String pw;
}
