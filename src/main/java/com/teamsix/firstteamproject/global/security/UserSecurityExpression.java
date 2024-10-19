package com.teamsix.firstteamproject.global.security;

import com.teamsix.firstteamproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityExpression {

    @Autowired
    UserRepository userRepository;

    public boolean isOwner(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // 현재 jwt로 인증된 사용자의 username

        // 현재 jwt로 인증된 사용자의 username과 userId가 같은지 확인
        return username.equals(userRepository.findUserById(userId).get().getEmail());
    }

}
