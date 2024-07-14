package com.teamsix.firstteamproject.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
public class RegistryForm {

    @NotNull
    private String name;

    //로그인에 사용
    @NotNull
    private String email;

    @NotNull
    private String pw;

}
