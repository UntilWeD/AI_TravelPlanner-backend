package com.teamsix.firstteamproject.user.Repository;

import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;

public interface UserRepository {
    // 유저 수정
    public RegistryForm saveUser(RegistryForm registryForm);
    public User updateUser(User user, Long id);
    public User deleteUser(User user);
}
