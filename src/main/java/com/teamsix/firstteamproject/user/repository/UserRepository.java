package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findUserByEmail(String email);

    public Optional<User> findUserByEmailAndPw(String email, String pw);

    @Modifying
    @Query(value = "UPDATE User u SET u.emailVerification = TRUE WHERE u.id = :id")
    public int updateEmailVerificationById(@Param("id") Long id);


}
