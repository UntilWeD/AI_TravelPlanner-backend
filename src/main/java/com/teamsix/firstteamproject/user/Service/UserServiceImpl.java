package com.teamsix.firstteamproject.user.Service;

import com.teamsix.firstteamproject.user.DTO.LoginForm;
import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;
import com.teamsix.firstteamproject.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public User login(LoginForm loginForm) {

        return null;
    }

    @Override
    public void logout(User user) {


    }
}
