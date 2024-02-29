package com.teamsix.firstteamproject.user.Repository;

import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;

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
