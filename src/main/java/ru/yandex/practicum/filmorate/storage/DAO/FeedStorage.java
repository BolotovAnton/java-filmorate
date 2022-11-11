package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.Feed;

import java.util.List;

public interface FeedStorage {

    List<Feed> getFeedByUserId(Integer userId);

    void addFeed(Integer userId, String eventType, String operation, Integer entityId);
}
