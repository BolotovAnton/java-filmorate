package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User add(User user);

    User update(User user);

    List<User> findAllUsers();

    User getUserById(Integer userId);

    List<User> getFriends(Integer userId);

    List<User> getCommonFriends(Integer userId, Integer friendId);
}
