package com.teamsix.firstteamproject.user.DTO;

import com.teamsix.firstteamproject.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String email;

    private String name;

    private boolean email_verification;

    static public UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .email_verification(user.isEmail_verification())
                .build();
    }
}
