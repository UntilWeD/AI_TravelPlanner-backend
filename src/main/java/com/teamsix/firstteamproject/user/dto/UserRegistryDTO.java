package com.teamsix.firstteamproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserRegistryDTO {

    @NotBlank
    private String name;

    //로그인에 사용
    @NotBlank
    private String email;

    @NotBlank
    private String pw;

}
