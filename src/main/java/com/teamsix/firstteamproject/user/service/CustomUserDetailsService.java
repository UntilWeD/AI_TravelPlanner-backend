package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.exception.UserEmailVerificationException;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    // email과 pw를 둘다 조건을 갖은채로 조회하기 수정필요
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if(!user.isEmailVerification()){
            throw new UserEmailVerificationException(email);
        }
        return createUserDetails(user);
    }

    //해당하는 User의 데이터가 존재한다면 UserDetails객체로 만들어서 return
    private UserDetails createUserDetails(User user){
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

}
