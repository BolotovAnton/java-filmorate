package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsExeption;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        if (userStorage.findAllUsers().stream().map(User::getLogin).anyMatch(x -> x.equals(user.getLogin()))) {
            throw new AlreadyExistsExeption("user with login " + user.getLogin() + " already exists");
        }
        return userStorage.add(user);
    }

    public User update(User user) {
        if (userStorage.findAllUsers().stream().map(User::getUserId).anyMatch(id -> id.equals(user.getUserId()))) {
            throw new NotFoundException("user with id=" + user.getUserId() + " not found");
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
        if (getUserById(friendId).getFriendIds().containsKey(userId)) {
            getUserById(userId).getFriendIds().put(friendId, FriendStatus.FRIEND);
            getUserById(friendId).getFriendIds().put(userId, FriendStatus.FRIEND);
        } else {
            getUserById(userId).getFriendIds().put(friendId, FriendStatus.REQUIRED);
        }
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
        return getUserById(userId).getFriendIds().entrySet()
                .stream()
                .filter(x -> x.getValue().equals(FriendStatus.FRIEND))
                .map(Map.Entry::getKey)
                .filter(x -> getUserById(friendId).getFriendIds().containsKey(x))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(Integer userId) {
        validateUserId(userId);
        return getUserById(userId).getFriendIds().entrySet()
                .stream()
                .filter(x -> x.getValue().equals(FriendStatus.FRIEND))
                .map(Map.Entry::getKey)
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public void validateUserId(Integer userId) {
        if (findAllUsers().stream().map(User::getUserId).noneMatch(x -> x.equals(userId))) {
            throw new NotFoundException("user with id=" + userId + " not found");
        }
    }

    public void deleteUserById(int userId) {
        userStorage.deleteUserById(userId);
    }
}
