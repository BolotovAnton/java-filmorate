package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.FriendListStorage;
import ru.yandex.practicum.filmorate.storage.DAO.UserStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;

    private final FriendListStorage friendListStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage,
                       FriendListStorage friendListStorage) {
        this.userStorage = userStorage;
        this.friendListStorage = friendListStorage;
    }

    public User add(User user) throws ValidationException {
        Validation.validateUser(user);
        return userStorage.addUser(user);
    }

    public User update(User user) throws ValidationException {
        Validation.validateUser(user);
        Validation.validateUserId(userStorage, user.getId());
        return userStorage.updateUser(user);
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User getUserById(Integer userId) throws ValidationException {
        Validation.validateUserId(userStorage, userId);
        return userStorage.getUserById(userId);
    }

    public void deleteUserById(int userId) throws ValidationException {
        Validation.validateUserId(userStorage, userId);
        userStorage.deleteUserById(userId);
    }

    public void addFriend(Integer userId, Integer friendId) throws ValidationException {
        Validation.validateUserId(userStorage, userId);
        Validation.validateUserId(userStorage, friendId);
        friendListStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId) throws ValidationException {
        Validation.validateUserId(userStorage, userId);
        Validation.validateUserId(userStorage, friendId);
        friendListStorage.deleteFriend(userId, friendId);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) throws ValidationException {
        Validation.validateUserId(userStorage, userId);
        Validation.validateUserId(userStorage, friendId);
        return userStorage.getCommonFriends(userId, friendId);
    }

    public List<User> getFriends(Integer userId) throws ValidationException {
        Validation.validateUserId(userStorage, userId);
        return userStorage.getFriends(userId);
    }
}
