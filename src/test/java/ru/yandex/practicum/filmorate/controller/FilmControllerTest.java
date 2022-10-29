package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.DAO.GenreStorage;
import ru.yandex.practicum.filmorate.storage.DAO.LikesStorage;
import ru.yandex.practicum.filmorate.storage.DAO.MPAStorage;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.inMemory.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    void init() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(),
                new MPAStorage() {
                    @Override
                    public List<MPA> getAllMPA() {
                        return null;
                    }

                    @Override
                    public MPA getMPAById(int mpaId) {
                        return null;
                    }
                }, new LikesStorage() {
            @Override
            public void addLike(int filmId, int userId) {
            }

            @Override
            public void deleteLike(int filmId, int userId) {
            }
        }, new GenreStorage() {
            @Override
            public List<Genre> getAllGenre() {
                return null;
            }

            @Override
            public Genre getGenreById(int genreId) {
                return null;
            }
        }, new UserService(new InMemoryUserStorage())));
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
