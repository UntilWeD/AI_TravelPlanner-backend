package com.teamsix.firstteamproject.user.service;


import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;


@Slf4j
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

//    @Test
//    void register() {

//        //given
//        UserDTO mockUserDTO =
//                UserDTO.builder()
//                        .email("muojeso90@gmail.com")
//                        .pw("12345678")
//                        .name("test01")
//                        .build();
//        User savedUser = mockUserDTO.toEntity();
//
//        //when
//        when(passwordEncoder.encode(mockUserDTO.getPw())).thenReturn("12345678");
//        when(userRepository.findUserByEmail(mockUserDTO.getEmail())).thenReturn(null);
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//
//        UserDTO result = userService.register(mockUserDTO);
//
//        //then
//        log.info("mockerUserDTO.getName : " + mockUserDTO.getName());
//        log.info("result : " + result.getName());
//        Assertions.assertThat(result.getEmail()).isEqualTo(mockUserDTO.getEmail());
//        Assertions.assertThat(result.getName()).isEqualTo(mockUserDTO.getName());
//        Assertions.assertThat(result.isEmailVerification()).isEqualTo(mockUserDTO.isEmailVerification());
//
//    }

//    @Test
//    void signIn() {
//
//        //given
//        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
//                .email("muojeso90@gmail.com")
//                .pw("12345678")
//                .build();
//        User user = Mockito.mock(User.class);
//        Authentication mockAuthentication = mock(Authentication.class);
//        JwtToken expectedToken = new JwtToken(1L,"USER",
//                "mockAccessToken", "mockRefreshToken");
//        AuthenticationManager mockAuthenticationManger = mock(AuthenticationManager.class);
//
//        when(authenticationManagerBuilder.getObject()).thenReturn(mockAuthenticationManger);
//        when(mockAuthenticationManger.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(mockAuthentication);
//        when(jwtTokenProvider.generateToken(mockAuthentication)).thenReturn(expectedToken);
//        when(userRepositoryJDBC.findUserByEmail(userLoginDTO.email)).thenReturn(Optional.of(user));
//
//        //when
//        JwtToken result = userService.signIn(userLoginDTO);
//
//        //then
//        Assertions.assertThat(expectedToken).isEqualTo(result);
//    }
//
//    /**
//     * 검증부분과 Optional.of()부분 후에 수정 필요
//     */
//    @Test
//    void setEmailVerify() {
//
//        //given
//        User user = User.builder()
//                .id(1L)
//                .email("test01@gmail.com")
//                .pw("12345678")
//                .name("test01")
//                .role("USER")
//                .emailVerification(true)
//                .build();
//        Long userId = 1L;
//
//        when(userRepositoryJDBC.setEmailVerifiedById(userId)).thenReturn(Optional.of(user));
//
//        //when
//        Optional<User> result = userService.setEmailVerify(userId);
//
//        //then
//        Assertions.assertThat(result.get().getEmailVerification())
//                .isEqualTo(user.getEmailVerification());
//    }
//
//    @Test
//    void findUserById() {
//
//        //given
//        Long userId = 1L;
//        User user = User.builder()
//                .id(1L)
//                .email("test01@gmail.com")
//                .pw("12345678")
//                .name("test01")
//                .role("USER")
//                .emailVerification(true)
//                .build();
//        UserDTO userDto = UserDTO.toDto(user);
//
//        /**
//         * try-with-resources 로 MockedStatic은 전역상태를 변경한다.
//         * 이 변경은 이 테스트 후에 다른 테스트에 영향을 줄 수 있어
//         * 바로 정리해야 하기에 이 try구문 후에
//         * MockedStatic객체가 자동으로 닫히고 staitc 메서드에 대한 모킹이 사라진다.
//         */
//        try (MockedStatic<UserDTO> mockedStatic = mockStatic(UserDTO.class)){
//            when(userRepositoryJDBC.findUserById(userId)).thenReturn(user);
//            when(UserDTO.toDto(user)).thenReturn(userDto);
//            mockedStatic.when(() -> UserDTO.toDto(user)).thenReturn(userDto);
//
//
//            //when
//            UserDTO result = userService.findUserById(userId);
//
//            //then
//            Assertions.assertThat(result).isEqualTo(userDto);
//        }
//
//    }
}