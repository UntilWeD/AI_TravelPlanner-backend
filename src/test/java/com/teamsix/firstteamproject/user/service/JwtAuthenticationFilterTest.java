package com.teamsix.firstteamproject.user.service;

import com.teamsix.firstteamproject.user.service.jwt.JwtAuthenticationFilter;
import com.teamsix.firstteamproject.user.service.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {
    @Mock
    JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 정상적인 과정
     */
    @Test
    void doFilter() throws ServletException, IOException {

        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = Mockito.mock(FilterChain.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        String accessToken = "testAccessToken";
        request.addHeader("Authorization", "Bearer " + accessToken);

        //when
        Mockito.when(jwtTokenProvider.validateToken(accessToken)).thenReturn(true);
        Mockito.when(jwtTokenProvider.getAuthentication(accessToken)).thenReturn(authentication);

        //then
        assertDoesNotThrow(() ->{
            jwtAuthenticationFilter.doFilter(request, response, filterChain);
        });
        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
        Mockito.verify(filterChain).doFilter(request, response);

        SecurityContextHolder.clearContext();
    }

    /**
     * 만료토큰
     */


}