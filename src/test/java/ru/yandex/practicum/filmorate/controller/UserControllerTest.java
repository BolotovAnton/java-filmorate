package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    UserController userController;
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    UserService userService = new UserService(inMemoryUserStorage);

    @BeforeEach
    void init() {
        userController = new UserController(userService);
    }

    @Test
    void userAdditionShouldTrowExceptionIfLoginIsNull() {
        User user = new User(null, "mail@mail.ru", LocalDate.of(1986, 4, 12));
        assertThrows(
                ValidationException.class,
                () -> userController.add(user)
        );
    }

    @Test
    void userAdditionShouldTrowExceptionIfEmailIsNull() {
        User user = new User("login", null, LocalDate.of(1986, 4, 12));
        assertThrows(
                ValidationException.class,
                () -> userController.add(user)
        );
    }

    @Test
    void userAdditionShouldTrowExceptionIfBirthdayIsNull() {
        User user = new User("login", "mail@mail.ru", null);
        assertThrows(
                ValidationException.class,
                () -> userController.add(user)
        );
    }

    @Test
    void userAdditionShouldTrowExceptionIfUserIsNull() {
        assertThrows(
                ValidationException.class,
                () -> userController.add(null)
        );
    }
}

