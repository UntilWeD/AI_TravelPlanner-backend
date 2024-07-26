package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.dto.RegistryForm;
import com.teamsix.firstteamproject.user.entity.Role;
import com.teamsix.firstteamproject.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@Transactional(readOnly = false)
public class UserRepositoryImpl implements UserRepository{

    private final NamedParameterJdbcTemplate template;

    //Source주입
    @Autowired
    public UserRepositoryImpl(DataSource dataSource){
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public RegistryForm saveUser(RegistryForm registryForm) {
        log.info("[UserRepository] Executing the saveUser method ");

        String sql = "INSERT INTO user(email, pw, name, role) " +
                "VALUES(:email, :pw, :name, :role)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("email", registryForm.getEmail())
                .addValue("pw", registryForm.getPw())
                .addValue("name", registryForm.getName())
                .addValue("role", Role.USER.name());
        template.update(sql, param);
        log.info("[UserRepository] Saving User = {}", registryForm);

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

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = :email";

        try{
            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("email", email);

            Optional<User> findUser =  Optional.of(template.queryForObject(
                    sql, param, new BeanPropertyRowMapper<>(User.class)));

            log.info("email = {} ", findUser.get().getEmail());
            log.info("pw = {} ", findUser.get().getPw());

            return findUser;
        } catch (EmptyResultDataAccessException ex){
            log.info("[유저리포지토리] findUser를 찾는 도중 오류가 발생하였습니다.");
            log.info("Error :  ", ex);
            return Optional.empty();
        }


    }

    @Override
    public Optional<User> setEmailVerifiedById(Long userId) {
        log.info("[UserRepository] setEmailVerifiedById Method is Executing.. Id : {}", userId);

        String sql = "UPDATE user SET email_verification= true WHERE id = :userId";
        Map<String, Object> param = Map.of("userId", userId);

        template.update(sql, param);

        log.info("Value is changed");

        sql = "SELECT * FROM user WHERE id = :userId";

        try{
            Optional<User> findUser = Optional.of(template.queryForObject(sql, param, new BeanPropertyRowMapper<>(User.class)));
            return findUser;
        } catch (Exception e){
            log.info("[UserRepostiory] While finding findUser, Error appeared...");
            return Optional.empty();
        }
    }




}
