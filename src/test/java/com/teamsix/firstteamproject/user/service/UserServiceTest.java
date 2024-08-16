package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.dto.LoginForm;
import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.dto.UserDto;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.entity.User;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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

    /**
     * 검증부분과 Optional.of()부분 후에 수정 필요
     */
    @Test
    void setEmailVerify() {

        //given
        User user = User.builder()
                .id(1L)
                .email("test01@gmail.com")
                .pw("12345678")
                .name("test01")
                .role("USER")
                .emailVerification(true)
                .build();
        Long userId = 1L;

        when(userRepository.setEmailVerifiedById(userId)).thenReturn(Optional.of(user));

        //when
        Optional<User> result = userService.setEmailVerify(userId);

        //then
        Assertions.assertThat(result.get().getEmailVerification())
                .isEqualTo(user.getEmailVerification());
    }

    @Test
    void findUserById() {

        //given
        Long userId = 1L;
        User user = User.builder()
                .id(1L)
                .email("test01@gmail.com")
                .pw("12345678")
                .name("test01")
                .role("USER")
                .emailVerification(true)
                .build();
        UserDto userDto = UserDto.toDto(user);

        /**
         * try-with-resources 로 MockedStatic은 전역상태를 변경한다.
         * 이 변경은 이 테스트 후에 다른 테스트에 영향을 줄 수 있어
         * 바로 정리해야 하기에 이 try구문 후에
         * MockedStatic객체가 자동으로 닫히고 staitc 메서드에 대한 모킹이 사라진다.
         */
        try (MockedStatic<UserDto> mockedStatic = mockStatic(UserDto.class)){
            when(userRepository.findUserById(userId)).thenReturn(user);
            when(UserDto.toDto(user)).thenReturn(userDto);
            mockedStatic.when(() -> UserDto.toDto(user)).thenReturn(userDto);


            //when
            UserDto result = userService.findUserById(userId);

            //then
            Assertions.assertThat(result).isEqualTo(userDto);
        }

    }
}