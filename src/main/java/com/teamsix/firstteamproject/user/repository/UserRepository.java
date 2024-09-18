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

        public Optional<User> findUserById(Long id);

        public Optional<User> findUserByEmail(String email);


    @Modifying
    @Query("UPDATE User u SET u.emailVerification = TRUE, u.role = :role WHERE u.id = :id")
    public int updateEmailVerificationById(@Param("id") Long id, @Param("role") String role);

    @Query("SELECT u.emailVerification FROM User u WHERE u.email = :email")
    public boolean findEmailVerificationByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.pw = :pw WHERE u.id = :id")
    public int updateNameAndPwById(String name, String pw, Long id);
}
