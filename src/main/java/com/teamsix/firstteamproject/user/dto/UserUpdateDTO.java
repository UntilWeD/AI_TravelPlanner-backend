package com.teamsix.firstteamproject.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserUpdateDTO {


    @NotNull
    @Setter
    private String pw;

    private String confirmationPw;

    private String name;

}
