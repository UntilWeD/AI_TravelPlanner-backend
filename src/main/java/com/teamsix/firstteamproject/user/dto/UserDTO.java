package com.teamsix.firstteamproject.user.dto;

import com.teamsix.firstteamproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    private String email;

    private String name;

    private String pw;

    private boolean emailVerification;

    public void encodingPw(String pw){
        this.pw = pw;
    }

    public User toEntity(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .pw(this.pw)
                .name(this.name)
                .emailVerification(this.emailVerification)
                .build();
    }

    static public UserDTO toDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .pw(user.getPw())
                .email(user.getEmail())
                .name(user.getName())
                .emailVerification(user.isEmailVerification())
                .build();
    }



}
