package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository {
    // 유저 CRUD
    public RegistryForm saveUser(RegistryForm registryForm);
    public User updateUser(User user, Long id);
    public User deleteUser(User user);

    //유저 조회
    public Optional<User> findUserByEmail(String email);

    public Boolean findEmailVerificationByEmail(String email);
    public User findUserById(Long id);

    //다른기능
    public Optional<User> setEmailVerifiedById(Long userId);
}
