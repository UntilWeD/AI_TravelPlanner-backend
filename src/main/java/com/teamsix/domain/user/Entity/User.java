package com.teamsix.domain.user.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    private Long id;
    private String name;
    private String password;
    private String email;
}

