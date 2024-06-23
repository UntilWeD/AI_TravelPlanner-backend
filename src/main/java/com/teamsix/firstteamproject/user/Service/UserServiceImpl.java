package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.JwtToken;
import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegistryForm register(RegistryForm registryForm) {
        log.info("register user = {}", registryForm);

        //닉네임 중복 체크 메서드 필요(나중에 jpa로?)

        registryForm.setPw(passwordEncoder.encode(registryForm.getPw()));
        RegistryForm registedUser = userRepository.saveUser(registryForm);


        return registedUser;
    }

    @Transactional
    @Override
    public JwtToken signIn(LoginForm loginForm) {

        log.info("LoginForm.email = {}", loginForm.getEmail());
        log.info("LoginForm.pw = {}", passwordEncoder.encode(loginForm.pw));

        //1. username + password를 기반으로 Authentication 객체 생성
        //이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.email, loginForm.pw);

        //2. 실제 검증. authenticate() 메서드를 통해 요청된 User에 대한 검증 진행
        // authenticated메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;

    }

    @Override
    public void logout(User user) {


    }

    @Override
    public Optional<User> setEmailVerify(Long userId) {
        log.info("[UserService] setEmailVerify Method is Executing...");
        Optional<User> findUser = userRepository.setEmailVerifiedById(userId);

        if(findUser.isEmpty()){
            log.info("해당 유저는 존재하지 않습니다.");
            return null;
        }
        return findUser;
    }
}
