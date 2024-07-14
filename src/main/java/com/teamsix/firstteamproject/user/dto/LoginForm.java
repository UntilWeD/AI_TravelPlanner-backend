package com.teamsix.firstteamproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// 뭐야 groups도 안쓰고 각 전송폼마다 알맞게 BeanValidation을 적용해 놓았네? 구우우우웃
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginForm {

    @NotBlank
    public String email;

    @NotBlank
    public String pw;

}
