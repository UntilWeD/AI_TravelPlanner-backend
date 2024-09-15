package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.exception.UserEmailVerificationException;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;


//    @Test
//    void loadUserByUsername_Succeed_WhenAllInputsAreValid() {
//
//        //given
//        String email = "test01@gmail.com";
//        User testUser = User.builder()
//                .email(email)
//                .pw("12345678")
//                .role("USER")
//                .emailVerification(true)
//                .build();
//        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));
//
//        //when
//        UserDetails result = customUserDetailsService.loadUserByUsername(email);
//
//        //then
//        Assertions.assertThat(result.getUsername()).isEqualTo(testUser.getEmail());
//        Assertions.assertThat(result.getPassword()).isEqualTo(testUser.getPw());
//        Assertions.assertThat(result.getAuthorities()).isNotNull();
//
//    }
//
//    @Test
//    void loadUserByUsername_throwsUserEmailVerificationException_IfEmailVerificationIsFalse() {
//
//        //given
//        String email = "test01@gmail.com";
//        User testUser = User.builder()
//                .email(email)
//                .pw("12345678")
//                .role("USER")
//                .emailVerification(false)
//                .build();
//        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));
//
//        //when
//
//
//        //then
//        Assertions.assertThatThrownBy(() ->{
//            customUserDetailsService.loadUserByUsername(email);
//        }).isInstanceOf(UserEmailVerificationException.class);
//    }
//
//    @Test
//    void loadUserByUsername_throwsUserNameNotFoundException_IfUserIsNull() {
//
//        //given
//        String email = "test01@gmail.com";
//        Mockito.when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());
//
//        //when
//
//
//        //then
//        Assertions.assertThatThrownBy(() ->{
//            customUserDetailsService.loadUserByUsername(email);
//        }).isInstanceOf(UsernameNotFoundException.class);
//    }
}