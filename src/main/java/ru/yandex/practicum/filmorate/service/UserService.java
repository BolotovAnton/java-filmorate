package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.FriendListStorage;
import ru.yandex.practicum.filmorate.storage.DAO.UserStorage;

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

    public void validateUserId(Integer userId) {
        if (findAllUsers().stream().map(User::getId).noneMatch(x -> x.equals(userId))) {
            throw new NotFoundException("user with id=" + userId + " not found");
        }
    }

    public User add(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        if (userStorage.findAllUsers().stream().map(User::getId).noneMatch(id -> id.equals(user.getId()))) {
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
        friendListStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        friendListStorage.deleteFriend(userId, friendId);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        validateUserId(userId);
        validateUserId(friendId);
        return userStorage.getCommonFriends(userId, friendId);
    }

    public List<User> getFriends(Integer userId) {
        validateUserId(userId);
        return userStorage.getFriends(userId);
    }
}
