package com.teamsix.firstteamproject.user.repository;

import com.teamsix.firstteamproject.user.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User mappedUser = User.builder()
            .id(rs.getLong("id"))
            .email(rs.getString("email"))
            .pw(rs.getString("pw"))
            .name(rs.getString("name"))
            .email_verification(rs.getBoolean("email_verification"))
            .build();

        mappedUser.getRoles().add(rs.getString("roles"));
        return mappedUser;
    }
}
