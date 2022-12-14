package ru.yandex.practicum.filmorate.storage.db.review;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.DAO.review.ReviewLikesStorage;

@Component
@RequiredArgsConstructor
public class ReviewLikesDbStorage implements ReviewLikesStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addReviewLike(Integer reviewId, Integer userId, boolean is_positive) {
        String sql = "INSERT INTO REVIEW_LIKES (REVIEW_ID, USER_ID, IS_POSITIVE) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reviewId, userId, is_positive);
    }

    @Override
    public void deleteReviewLike(Integer reviewId, Integer userId) {
        String sql = "DELETE FROM REVIEW_LIKES WHERE REVIEW_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sql, reviewId, userId);
    }
}
