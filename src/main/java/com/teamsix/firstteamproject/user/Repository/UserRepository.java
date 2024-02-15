package com.teamsix.firstteamproject.user.Repository;

import com.teamsix.firstteamproject.user.Entity.User;

public interface UserRepository {
    // 유저 수정
    public User saveUser(User user);
    public User updateUser(User user, Long id);
    public User deleteUser(User user);
}
