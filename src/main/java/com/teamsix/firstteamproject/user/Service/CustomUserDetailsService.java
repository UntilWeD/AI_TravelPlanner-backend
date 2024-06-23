package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));

    }

    // 아직 비밀번호는 암호화되지 않았음
    //해당하는 User의 데이터가 존재한다면 UserDetails객체로 만들어서 return
    private UserDetails createUserDetails(User user){
        log.info("loadUserByUsername : 메서드 실행~");
        log.info("User.email : {}", user.getEmail());
        log.info("User.pw : {}", user.getPw());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
