package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    UserController userController;

    @BeforeEach
    void init() {
        userController = new UserController();
    }

    @Test
    void userAdditionShouldTrowExceptionIfLoginIsNull() {
        User user = new User(null, "mail@mail.ru", LocalDate.of(1986, 4, 12));

        assertThrows(
                NullPointerException.class,
                () -> userController.add(user)
        );
    }

    @Test
    void userAdditionShouldTrowExceptionIfEmailIsNull() {
        User user = new User("login", null, LocalDate.of(1986, 4, 12));

        assertThrows(
                NullPointerException.class,
                () -> userController.add(user)
        );
    }

    @Test
    void userAdditionShouldTrowExceptionIfBirthdayIsNull() {
        User user = new User("login", "mail@mail.ru", null);

        assertThrows(
                NullPointerException.class,
                () -> userController.add(user)
        );
    }

    @Test
    void userAdditionShouldTrowExceptionIfUserIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> userController.add(null)
        );
    }
}

