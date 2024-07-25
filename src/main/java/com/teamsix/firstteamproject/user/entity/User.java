package com.teamsix.firstteamproject.user.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@AllArgsConstructor
@Getter
@Builder
@Setter
@NoArgsConstructor
public class User implements UserDetails {



    //유저 식별번호
    private Long id;

    //유저이름
    private String name;

    //로그인에 사용
    private String email;
    private String pw;

    private boolean email_verification;


    @Builder.Default
    private List<String> roles = new ArrayList<>();


    // roles 필드를 GrantedAuthority 객체의 컬렉션으로 변환한다.
    // 그리고 SpringSecurity는 이 권한을 사용하여 인증 및 인가를 처리한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    //나머지는 계정상태를 나타냄.

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

