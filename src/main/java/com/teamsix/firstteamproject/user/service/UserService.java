package com.teamsix.firstteamproject.user.service;


import com.teamsix.firstteamproject.user.dto.UserDTO;
import com.teamsix.firstteamproject.user.dto.UserUpdateDTO;
import com.teamsix.firstteamproject.user.entity.JwtToken;
import com.teamsix.firstteamproject.user.exception.UserAlreadyExistsException;
import com.teamsix.firstteamproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService{
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserDTO register(UserDTO dto) {
        if(userRepository.findUserByEmail(dto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(dto.getEmail());
        }

        // 서비스 레이어에서 해당 인코딩도 비즈니스 로직이기에 적합하다.
        dto.encodingPw(passwordEncoder.encode(dto.getPw()));
        return userRepository.save(dto.toEntity()).toDTO();
    }


    public JwtToken signIn(UserDTO dto) {

        //1. username + password를 기반으로 Authentication 객체 생성
        //이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPw());

        //2. 실제 검증. authenticate() 메서드를 통해 요청된 User에 대한 검증 진행
        // authenticated메서드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메서드 실행
        // 만약 유저가 존재하지 않을시에 UsernameNotFoundException 발생
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        jwtToken.setUserId(userRepository.findUserByEmail(dto.getEmail()).get().getId());

        return jwtToken;
    }


    public void setEmailVerify(Long userId) {
        userRepository.updateEmailVerificationById(userId);
    }

    public UserDTO findUserById(Long userId){
        return UserDTO.toDto(userRepository.findUserById(userId).get());
    }

    public UserDTO updateUser(Long userId, UserUpdateDTO dto) {
        if(!dto.getPw().equals(dto.getConfirmationPw())){
            throw new RuntimeException("User pw and confirmationPw is not equal.");
        }
        dto.setPw(passwordEncoder.encode(dto.getPw()));
        userRepository.updateNameAndPwById(dto.getName(), dto.getPw(), userId);
        return userRepository.findUserById(userId).get().toDTO();
    }

    public Long deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return userId;
    }

    private boolean verifyPassword(String rawPw, String encodedPw){
        return passwordEncoder.matches(rawPw, encodedPw);
    }

}
