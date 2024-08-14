package com.teamsix.firstteamproject.user.entity;

import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {


    //유저 식별번호
    @Id
    @GeneratedValue
    private Long id;

    //유저이름
    private String name;

    //로그인에 사용
    private String email;
    private String pw;

    private boolean emailVerification;
    private String role;


//    /**
//     * TravelPlan과의 양방향관게에서 주인은 TravelPlan이기에
//     * travelPlans에 TravelPlan객체를 저장해도 외래키를 관리하지 않기에 저장되지 않는다.(연관관계)
//     */
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<TravelPlan> travelPlans;


    // roles 필드를 GrantedAuthority 객체의 컬렉션으로 변환한다.
    // 그리고 SpringSecurity는 이 권한을 사용하여 인증 및 인가를 처리한다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role));
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


    public boolean getEmailVerification(){
        return emailVerification;
    }
}

