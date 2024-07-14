package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    // 유저 수정
    public RegistryForm saveUser(RegistryForm registryForm);
    public User updateUser(User user, Long id);
    public User deleteUser(User user);

    public Optional<User> findUserByEmail(String email);

    //다른기능
    public Optional<User> setEmailVerifiedById(Long userId);
}
