package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component("UserDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {

        String sql = "insert into users (user_name, login, email, birthday) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getLogin(), user.getEmail(), user.getBirthday());

//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
//            stmt.setString(1, user.getName());
//            stmt.setString(2, user.getLogin());
//            stmt.setString(3, user.getEmail());
//            stmt.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
//            return stmt;
//        }, keyHolder);
//        System.out.println(keyHolder.getKey());
//        return getUserById(Objects.requireNonNull(keyHolder.getKey()).intValue());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        return getUserById(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("login"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate()));
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "select * from users where user_id = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("login"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate()), userId);
        return users.get(0);
    }

    @Override
    public void deleteUserById(int userId) {
        String sql = "SET REFERENTIAL_INTEGRITY = FALSE; " +
                "BEGIN TRANSACTION; " +
                "DELETE FROM users WHERE user_id = ?;" +
                "COMMIT;" +
                "SET REFERENTIAL_INTEGRITY = TRUE";
        jdbcTemplate.update(sql, userId);
    }
}
