package ru.yandex.practicum.filmorate.storage.DAO;

public interface FriendListStorage {

    void addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);
}
