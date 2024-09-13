package com.teamsix.firstteamproject.user.repository;


import com.teamsix.firstteamproject.user.dto.UserUpdateDTO;
import com.teamsix.firstteamproject.user.entity.User;

import java.util.Optional;


public interface UserRepositoryJDBC {


    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO);
    public Long deleteUser(Long userId);

    //유저 조회
    public Optional<User> findUserByEmail(String email);

    public Boolean findEmailVerificationByEmail(String email);
    public User findUserById(Long id);

    //다른기능
    public Optional<User> setEmailVerifiedById(Long userId);
}
