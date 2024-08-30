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

    static public UserDTO toDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .pw(user.getPw())
                .email(user.getEmail())
                .name(user.getName())
                .emailVerification(user.getEmailVerification())
                .build();
    }
}
