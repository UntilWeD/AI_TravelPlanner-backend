package com.teamsix.firstteamproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * 단순하고 직관적이며 널리 사용되는 Bearer 인증방식을 사용할 것이다.
 * 이 인증 방식은 Access Token을 HTTP요청의 Authorization헤더에 포함하여 전송한다.
 */
@Builder
@Data
@AllArgsConstructor
public class JwtToken {

    // JWT에 대한 인증 타입
    private String grantType;
    private String accessToken;
    private String refreshToken;

}
