package com.teamsix.firstteamproject.user.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 뭐야 groups도 안쓰고 각 전송폼마다 알맞게 BeanValidation을 적용해 놓았네? 구우우우웃
@Data
public class LoginForm {

    @NotBlank
    public String email;

    @NotBlank
    public String pw;

}
