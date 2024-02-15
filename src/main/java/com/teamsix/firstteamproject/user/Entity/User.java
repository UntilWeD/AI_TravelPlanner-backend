package com.teamsix.firstteamproject.user.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class User {

    //유저 식별번호
    private Long id;

    //유저이름
    private String name;

    //로그인에 사용
    private String email;
    private String pw;

}

