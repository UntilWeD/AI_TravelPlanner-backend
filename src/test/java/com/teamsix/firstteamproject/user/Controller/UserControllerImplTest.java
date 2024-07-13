package com.teamsix.firstteamproject.user.Controller;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.JwtToken;
import com.teamsix.firstteamproject.user.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class UserControllerImplTest {

    @Autowired
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomServerPort;

    private RegistryForm registryForm;

    @BeforeEach
    void beforeEach(){
        // Member 회원가입
        registryForm = RegistryForm.builder()
                .email("test1@gmail.com")
                .pw("12345678")
                .name("닉네임1")
                .build();
    }

    @AfterEach
    void afterEach(){
        //아직 데이터베이스 초기화 없음
    }

    @Test
    void register() {
        //API 요청설정
        String url = "http://localhost:" + randomServerPort + "/user/registry";
        ResponseEntity<RegistryForm> responseEntity = testRestTemplate.
                postForEntity(url, registryForm, RegistryForm.class);

        //응답검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        RegistryForm savedRegistryForm = responseEntity.getBody();

        assertThat(savedRegistryForm.getEmail()).isEqualTo(registryForm.getEmail());
        assertThat(savedRegistryForm.getName()).isEqualTo(registryForm.getName());
    }

    @Test
    void signIn() {
        userService.register(registryForm);

        LoginForm loginForm = LoginForm.builder()
                .email("test1@gmail.com")
                .pw("12345678")
                .build();

        //로그인 요청
        JwtToken jwtToken = userService.signIn(loginForm);

        //HttpHeaders 객체 생성 및 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken.getAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("httpHeaders = {}", httpHeaders);

        //API 요청 설정
        String url = "http://localhost:" + randomServerPort + "/user/test";
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(
                url, new HttpEntity<>(httpHeaders), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(loginForm.getEmail());
    }
}