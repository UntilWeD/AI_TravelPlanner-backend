package com.teamsix.firstteamproject.user.Repository;

import com.teamsix.firstteamproject.user.DTO.RegistryForm;
import com.teamsix.firstteamproject.user.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public RegistryForm saveUser(RegistryForm registryForm) {
        log.info("[UserRepository] Executing the saveUser method ");

        String sql = "INSERT INTO user(id, email, pw, name) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                registryForm.getId(), registryForm.getEmail(), registryForm.getPw(), registryForm.getName());

        log.info("[UserRepository] We Saved this User = {}", registryForm);

        return registryForm;
    }

    @Override
    public User updateUser(User user, Long id) {
        return null;
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }


}
