package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.storage.DAO.FeedStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FeedDbStorage implements FeedStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Feed> getFeedByUserId(Integer userId) {
        String sql = "SELECT * FROM FEEDS WHERE USER_ID = ? ORDER BY TIMESTAMP";
        return jdbcTemplate.query(sql, this::makeFeed, userId);
    }

    @Override
    public void addFeed(Integer userId, String eventType, String operation, Integer entityId) {
        String sql = "INSERT INTO FEEDS (TIMESTAMP, USER_ID, EVENT_TYPE, OPERATION, ENTITY_ID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, Instant.now().toEpochMilli(), userId, eventType, operation, entityId);
    }

    private Feed makeFeed(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("event_id");
        int userId = rs.getInt("user_id");
        long timestamp = rs.getLong("timestamp");
        String eventType = rs.getString("event_type");
        String operation = rs.getString("operation");
        int entityId = rs.getInt("entity_id");
        return new Feed(id, userId, timestamp, eventType, operation, entityId);
    }
}
