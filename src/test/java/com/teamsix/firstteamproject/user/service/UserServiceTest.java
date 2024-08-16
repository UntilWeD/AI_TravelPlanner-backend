package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp(){
    }

    @Test
    void register() {

        //given
        RegistryForm mockRegistryForm =
                RegistryForm.builder()
                        .email("muojeso90@gmail.com")
                        .pw("12345678")
                        .name("test01")
                        .build();

        //when
        when(userRepository.saveUser(mockRegistryForm)).thenReturn(mockRegistryForm);
        when(passwordEncoder.encode(mockRegistryForm.getPw())).thenReturn("12345678");
        when(userRepository.findUserByEmail(mockRegistryForm.getEmail())).thenReturn(Optional.empty());
        RegistryForm result = userService.register(mockRegistryForm);

        //then
        Assertions.assertThat(mockRegistryForm).isEqualTo(result);

    }

    @Test
    void signIn() {

        //given
        LoginForm loginForm = LoginForm.builder()
                .email("muojeso90@gmail.com")
                .pw("12345678")
                .build();
        Authentication mockAuthentication = mock(Authentication.class);
        JwtToken expectedToken = new JwtToken("USER",
                "mockAccessToken", "mockRefreshToken");
        AuthenticationManager mockAuthenticationManger = mock(AuthenticationManager.class);

        when(authenticationManagerBuilder.getObject()).thenReturn(mockAuthenticationManger);
        when(mockAuthenticationManger.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);
        when(jwtTokenProvider.generateToken(mockAuthentication)).thenReturn(expectedToken);

        //when
        JwtToken result = userService.signIn(loginForm);

        //then
        Assertions.assertThat(expectedToken).isEqualTo(result);
    }

    @Test
    void setEmailVerify() {
    }

    @Test
    void findUserById() {

        //given



        //when


        //then
    }
}