package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User add(@Valid @RequestBody User user) throws ValidationException {
        user = userService.add(user);
        log.debug("user has been added");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        userService.update(user);
        log.debug("user has been updated");
        return user;
    }

    @GetMapping
    public List<User> findAllUsers() {
        log.debug("amount of users: {}", userService.findAllUsers().size());
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) throws ValidationException {
        User user = userService.getUserById(userId);
        log.debug("user with id={} has been found", userId);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable int userId) throws ValidationException {
        userService.deleteUserById(userId);
        log.debug("user with id={} has been deleted", userId);
    }

    @PutMapping("/{userId}/friends/{friendsId}")
    public void addFriend(@PathVariable Integer userId, @PathVariable Integer friendsId) throws ValidationException {
        userService.addFriend(userId, friendsId);
        log.debug("friend with id={} has been added for user with id={}", friendsId, userId);
    }

    @DeleteMapping("/{userId}/friends/{friendsId}")
    public void deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendsId) throws ValidationException {
        userService.deleteFriend(userId, friendsId);
        log.debug("friend with id={} has been deleted for user with id={}", friendsId, userId);
    }

    @GetMapping("/{userId}/friends/common/{friendsId}")
    public List<User> getCommonFriends(@PathVariable Integer userId, @PathVariable Integer friendsId) throws ValidationException {
        List<User> commonFriends = userService.getCommonFriends(userId, friendsId);
        log.debug("amount of common friends for users with ids {} and {} is {}", userId, friendsId, commonFriends.size());
        return commonFriends;
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable Integer userId) throws ValidationException {
        List<User> friends = userService.getFriends(userId);
        log.debug("amount of friends for user with id={} is {}", userId, friends.size());
        return friends;
    }

    @GetMapping("/{userId}/feed")
    public List<Feed> getFeedByUser(@PathVariable Integer userId) throws ValidationException {
        List<Feed> feeds = userService.getFeedByUserId(userId);
        log.debug("feeds length of user with id = {} is {}", userId, feeds.size());
        return feeds;
    }
}

