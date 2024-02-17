package com.teamsix.firstteamproject.user.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.Getter;

@Data
public class RegistryForm {

    @Null
    private Long id;

    @NotNull
    private String name;

    //로그인에 사용
    @NotNull
    private String email;

    @NotNull
    private String pw;
}
