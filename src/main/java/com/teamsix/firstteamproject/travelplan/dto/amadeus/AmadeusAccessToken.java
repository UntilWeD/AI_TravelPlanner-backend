package com.teamsix.firstteamproject.travelplan.dto.amadeus;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class AmadeusAccessToken {

    // 리소스 타입
    private String type;

    // 해당 유저 이메일 주소
    private String username;

    //애플리케이션 이름
    private String application_name;

    //해당 애플리케이션의 API_key
    private String client_id;

    //Bearer타입
    private String token_type;

    //인증받기 위한 accessToken
    private String access_token;

    //만료일
    private long expires_in;

    //상태 (approved, expired)
    private String state;
    private String scope;

    private Instant issuedAt; // 토큰이 발급된 시각 ( epoch 시각 )


    public boolean isExpired(){
        return issuedAt.plusSeconds(expires_in).isBefore(Instant.now());
    }

}
