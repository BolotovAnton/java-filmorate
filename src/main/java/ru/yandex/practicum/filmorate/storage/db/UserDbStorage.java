package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component("UserDbStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User add(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        return getUserById(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE USERS SET " + "LOGIN = ? , USER_NAME = ?, EMAIL = ?, BIRTHDAY = ? " +
                "WHERE USER_ID = ?";
        jdbcTemplate.update(sql, user.getLogin(), user.getName(), user.getEmail(), user.getBirthday(), user.getId());
        return getUserById(user.getId());
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeUser(rs), userId);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        String sql = "SELECT * FROM USERS AS U WHERE U.USER_ID IN " +
                "(SELECT FL.FRIEND_ID FROM FRIEND_LIST AS FL WHERE FL.USER_ID = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        String sql = "SELECT * FROM USERS AS U, FRIEND_LIST AS FL1, FRIEND_LIST AS FL2 " +
                "WHERE U.USER_ID = FL1.FRIEND_ID and U.USER_ID = FL2.FRIEND_ID AND " +
                "FL1.USER_ID = ? AND FL2.USER_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId, friendId);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String login = rs.getString("login");
        String name = rs.getString("user_name");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, login, name, email, birthday);
    }
}
