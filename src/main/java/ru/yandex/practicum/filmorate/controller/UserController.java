package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    private void validate(User user) throws ValidationException {

        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            ValidationException e = new ValidationException("wrong login");
            log.debug("Ошибка: {}", e.getMessage());
            throw e;
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("username has changed to login");
        }
    }

    @PostMapping
    public User add(@Valid @RequestBody User user) throws ValidationException {
        validate(user);
        user.setId(idCounter);
        idCounter++;
        users.put(user.getId(), user);
        log.info("user has been added");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        validate(user);
        if (!users.containsKey(user.getId())) {
            ValidationException e = new ValidationException("users doesn't contain the user");
            log.debug("Ошибка: {}", e.getMessage());
            throw e;
        }
        users.put(user.getId(), user);
        log.info("user has been changed");
        return user;
    }

    @GetMapping
    public List<User> findAllUsers() {
        log.info("Current number of users: {}", users.size());
        return new ArrayList<>(users.values());
    }
}

