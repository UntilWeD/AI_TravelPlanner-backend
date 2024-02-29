package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public RegistryForm register(RegistryForm registryForm) {
        log.info("register user = {}", registryForm);

        RegistryForm registedUser = userRepository.saveUser(registryForm);

        return registedUser;
    }

    @Override
    public Optional<User> login(LoginForm loginForm) {
        Optional<User> loginUser = userRepository.findUserByEmail(loginForm.email);

        //TODO
        //Spring에서 배운거 써먹기 (validator, error) 사용하기
        if(loginUser.get() == null){
            log.info("해당 데이터 베이스에 없는 사용자 id입니다. ");
            return null;
        }

        if(loginUser.get().getPw().equals(loginForm.pw)){
            log.info("Login Successfull!");
            return loginUser;
        } else {
            log.info("Login Failed!..");
            return null;
        }

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
