package com.teamsix.firstteamproject.user.service;


import com.teamsix.firstteamproject.user.entity.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 4시간동안 시간 꼬라박고 원인이 뭔가 코드에 없다는걸 눈치채고 보니
 * JwtTokenProvider에 @Mock애너테이션을 두어 가상객체로 되었기 떄문에
 * 내부 메서드를 검증하지 못했던것 그래서 계속 NULL이 나왔다.
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private static final String SECRET_KEY = "verySecretKeyForTestingPurposesOnly12345678901234567890";
    private Key key;
    long ONE_DAY_IN_SECONDS = 86400000;


    @BeforeEach
    void setUp() {
        this.key = generateKey(SECRET_KEY);
        jwtTokenProvider = new JwtTokenProvider(SECRET_KEY);
    }

    private Key generateKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * 인증로직만 Mock으로 가상객체로 하여 분리한다.
     * Jwt토큰생성테스트는 단순히 메서드만 검증하는순수한 단위테스트보단
     * 실제로 Jwt 토큰이 제대로 생성되었는지 확인하여 더 실용성 있는 테스트를 만든다.
     * 단위테스트보단 컴포넌트 테스트에 가까워지지만 이방식이 가장 효율적이라 생각한다.
     */
    @Test
    void generateToken() {

        //given
        Authentication mockAuthentication = mock(Authentication.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("USER")
        );
        when(mockAuthentication.getAuthorities()).thenReturn((Collection) authorities);
        when(mockAuthentication.getName()).thenReturn("testUser");

        //when
        JwtToken result = jwtTokenProvider.generateToken(mockAuthentication);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getGrantType()).isEqualTo("Bearer");
        assertThat(result.getAccessToken()).isNotBlank();
        assertThat(result.getRefreshToken()).isNotBlank();

        // Access 토큰 검증
        Claims accessTokenClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(result.getAccessToken())
                .getBody();

        assertThat(accessTokenClaims.getSubject()).isEqualTo("testUser");
        assertThat(accessTokenClaims.get("auth")).isEqualTo("USER");

        Date now = new Date();
        Date expectedExpiration= new Date(now.getTime() + ONE_DAY_IN_SECONDS); // 토큰 만료시간
        long allowedDifference = 5000; // 허용된 5초의 오차시간

        assertThat(accessTokenClaims.getExpiration().getTime())
                .isCloseTo(expectedExpiration.getTime(), within(allowedDifference));

        // Refresh 토큰 검증
        Claims refreshTokenClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(result.getRefreshToken())
                .getBody();

        assertThat(refreshTokenClaims.getExpiration().getTime())
                .isCloseTo(expectedExpiration.getTime(), within(allowedDifference));
    }


    /**
     * parseClaims가 private이기 때문에 어쩔수 없이 jwt토큰 생성(통합테스트성격)
     */
    @Test
    void getAuthentication() {

        //given
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("USER")
        );

        Date accessTokenExpiresln = new Date((new Date()).getTime() + ONE_DAY_IN_SECONDS);
        String accessToken = Jwts.builder()
                .setSubject("testUser")
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresln)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        UserDetails principal = new User("testUser", "", authorities);
        Authentication expectedAuthentication
                = new UsernamePasswordAuthenticationToken(principal,"", authorities);

        //when
        Authentication result = jwtTokenProvider.getAuthentication(accessToken);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(expectedAuthentication.getName());
        assertThat(result.getDetails()).isEqualTo(expectedAuthentication.getDetails());
        assertThat(result.getCredentials()).isEqualTo(expectedAuthentication.getCredentials());
        assertThat(result.getAuthorities().toString().contains("USER")).isTrue();
    }

    @Test
    void validateToken() {

        //given
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("USER")
        );

        Instant expiration = Instant.now();
        String accessToken = Jwts.builder()
                .setSubject("testUser")
                .claim("auth", authorities)
                .setExpiration(Date.from(expiration.plusSeconds(ONE_DAY_IN_SECONDS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String expiredAccessToken =  Jwts.builder()
                .setSubject("testUser")
                .claim("auth", authorities)
                .setExpiration(Date.from(expiration.minusSeconds(ONE_DAY_IN_SECONDS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //when
        boolean result = jwtTokenProvider.validateToken(accessToken);

        //then

        //정상검증
        assertThat(result).isTrue();
        //만료 검증
        Assertions.assertThatThrownBy(() -> {
            jwtTokenProvider.validateToken(expiredAccessToken);
        }).isInstanceOf(ExpiredJwtException.class);


    }
}