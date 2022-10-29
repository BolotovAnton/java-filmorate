package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        user = userService.add(user);
        log.debug("user has been added");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
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
    public User getUserById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        log.debug("user with id={} has been found", userId);
        return user;
    }

    @PutMapping("/{userId}/friends/{friendsId}")
    public void addFriend(@PathVariable Integer userId, @PathVariable Integer friendsId) {
        userService.addFriend(userId, friendsId);
        log.debug("friend with id={} has been added for user with id={}", friendsId, userId);
    }

    @DeleteMapping("/{userId}/friends/{friendsId}")
    public void deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendsId) {
        userService.deleteFriend(userId, friendsId);
        log.debug("friend with id={} has been deleted for user with id={}", friendsId, userId);
    }

    @GetMapping("/{userId}/friends/common/{friendsId}")
    public List<User> getCommonFriends(@PathVariable Integer userId, @PathVariable Integer friendsId) {
        List<User> commonFriends = userService.getCommonFriends(userId, friendsId);
        log.debug("amount of common friends for users with ids {} and {} is {}", userId, friendsId, commonFriends.size());
        return commonFriends;
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable Integer userId) {
        List<User> friends = userService.getFriends(userId);
        log.debug("amount of friends for user with id={} is {}", userId, friends.size());
        return friends;
    }

    private void validateUser(User user) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("wrong login");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("username is empty, username has been changed to login");
        }
    }
}

