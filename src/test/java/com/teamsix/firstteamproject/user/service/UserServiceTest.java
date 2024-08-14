package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


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

        //given
        User testUser = User.builder()
                .email("muojeso90@gmail.com")
                .pw("1234")
                .name("test01")
                .build();
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
        Mockito.when(userRepository.saveUser(mockRegistryForm)).thenReturn(mockRegistryForm);
        Mockito.when(passwordEncoder.encode(mockRegistryForm.getPw())).thenReturn("12345678");
        Mockito.when(userRepository.findUserByEmail(mockRegistryForm.getEmail())).thenReturn(Optional.empty());
        RegistryForm result = userService.register(mockRegistryForm);

        //then
        Assertions.assertThat(mockRegistryForm).isEqualTo(result);

    }

    @Test
    void signIn() {
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