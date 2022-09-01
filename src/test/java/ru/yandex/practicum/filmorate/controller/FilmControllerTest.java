package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    void init() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(),
                new UserService(new InMemoryUserStorage())));
    }

    @Test
    void filmAdditionShouldTrowExceptionIfNameIsNull() {
        Film film = new Film(null, "description", LocalDate.of(2021, 12, 1), 90);

        assertThrows(
                NullPointerException.class,
                () -> filmController.add(film)
        );
    }

    @Test
    void filmAdditionShouldTrowExceptionIfDescriptionIsNull() {
        Film film = new Film("film", null, LocalDate.of(2021, 12, 1), 90);

        assertThrows(
                NullPointerException.class,
                () -> filmController.add(film)
        );
    }

    @Test
    void filmAdditionShouldTrowExceptionIfReleaseDateIsNull() {
        Film film = new Film("film", "description", null, 90);

        assertThrows(
                NullPointerException.class,
                () -> filmController.add(film)
        );
    }

    @Test
    void filmAdditionShouldTrowExceptionIfFilmIsNull() {

        assertThrows(
                NullPointerException.class,
                () -> filmController.add(null)
        );
    }


}
