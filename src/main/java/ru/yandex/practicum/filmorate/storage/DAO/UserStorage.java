package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    User updateUser(User user);

    List<User> findAllUsers();

    User getUserById(Integer userId);

    List<User> getFriends(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer friendId);

    boolean dbContainsUser(Integer reviewId);

    void deleteUserById(int userId);
}
