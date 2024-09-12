package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByEmail(String email);


}
