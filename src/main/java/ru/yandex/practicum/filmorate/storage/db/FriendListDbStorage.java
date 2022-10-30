package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.DAO.FriendListStorage;

@Component
@RequiredArgsConstructor
public class FriendListDbStorage implements FriendListStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sql = "INSERT INTO FRIEND_LIST(USER_ID, FRIEND_ID) " + "VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM FRIEND_LIST WHERE USER_ID = ? AND FRIEND_ID = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }
}
