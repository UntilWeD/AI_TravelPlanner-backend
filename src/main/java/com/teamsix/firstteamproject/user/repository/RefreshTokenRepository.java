package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{

}
