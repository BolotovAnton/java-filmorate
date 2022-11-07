package ru.yandex.practicum.filmorate.storage.DAO.review;

public interface ReviewLikesStorage {

    void addReviewLike(Integer reviewId, Integer userId, boolean isPositive);

    void deleteReviewLike(Integer reviewId, Integer userId);
}