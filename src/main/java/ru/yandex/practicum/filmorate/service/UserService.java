package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsExeption;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        if (userStorage.getUsers().values().stream().map(User::getLogin).anyMatch(x -> x.equals(user.getLogin()))) {
            throw new AlreadyExistsExeption("user with login " + user.getLogin() + " already exists");
        }
        return userStorage.add(user);
    }

    public User update(User user) {
        if (!userStorage.getUsers().containsKey(user.getId())) {
            throw new NotFoundException("user with id=" + user.getId() + " not found");
        }
        return userStorage.update(user);
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User getUserById(Integer userId) {
        validateUserId(userId);
        return userStorage.getUserById(userId);
    }

    public void addFriend(Integer userId, Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        getUserById(userId).getFriendIds().add(friendId);
        getUserById(friendId).getFriendIds().add(userId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        getUserById(userId).getFriendIds().remove(friendId);
        getUserById(friendId).getFriendIds().remove(userId);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        List<User> commonFriendIds = new ArrayList<>();
        for (Integer integer1: getUserById(userId).getFriendIds()) {
            for (Integer integer2: getUserById(friendId).getFriendIds()) {
                if (integer1.equals(integer2)) {
                    commonFriendIds.add(userStorage.getUserById(integer1));
                }
            }
        }
        return commonFriendIds;
    }

    public List<User> getFriends(Integer userId) {
        validateUserId(userId);
        List<User> friends = new ArrayList<>();
        for (Integer integer: userStorage.getUserById(userId).getFriendIds()) {
            friends.add(getUserById(integer));
        }
        return friends;
    }

    public void validateUserId(Integer userId) {
        if (findAllUsers().stream().map(User::getId).noneMatch(x -> x.equals(userId))) {
            throw new NotFoundException("user with id=" + userId + " not found");
        }
    }
}
