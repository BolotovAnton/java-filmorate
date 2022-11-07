package ru.yandex.practicum.filmorate.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.FilmStorage;
import ru.yandex.practicum.filmorate.storage.DAO.GenreStorage;
import ru.yandex.practicum.filmorate.storage.DAO.MPAStorage;
import ru.yandex.practicum.filmorate.storage.DAO.UserStorage;
import ru.yandex.practicum.filmorate.storage.DAO.review.ReviewStorage;

import java.time.LocalDate;

@Slf4j
@Component
@AllArgsConstructor
public class Validation {

    public static void validateReviewId(ReviewStorage reviewStorage, Integer reviewId) throws ValidationException {
        if (reviewId != null && (reviewId <= 0 || !reviewStorage.dbContainsReview(reviewId))) {
            throw new ValidationException("invalid value of reviewId = " + reviewId);
        }
    }

    public static void validateCountOfLimit(Integer count) throws ValidationException {
        if (count <= 0) {
            throw new ValidationException("invalid value of count = " + count);
        }
    }

    public static void validateFilmId(FilmStorage filmStorage, Integer filmId) throws ValidationException {
        if (filmId != null && (filmId <= 0 || !filmStorage.dbContainsFilm(filmId))) {
            throw new ValidationException("film with id = " + filmId + " not found");
        }
    }

    public static void validateFilm(Film film) throws ValidationException {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("too long description");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("date of release should be after 28.12.1895");
        }
    }

    public static void validateUserId(UserStorage userStorage, Integer userId) throws ValidationException {
        if (userId != null && (userId <= 0 || !userStorage.dbContainsUser(userId))) {
            throw new ValidationException("user with id = " + userId + " not found");
        }
    }

    public static void validateUser(User user) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            throw new ValidationException("wrong login");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("username is empty, username has been changed to login");
        }
    }

    public static void validateGenreId(GenreStorage genreStorage, Integer genreId) throws ValidationException {
        if (genreId != null && (genreId <= 0 || !genreStorage.dbContainsGenre(genreId))) {
            throw new ValidationException("genre with id = " + genreId + " not found");
        }
    }

    public static void validateMPAId(MPAStorage mpaStorage, Integer mpaId) throws ValidationException {
        if (mpaId != null && (mpaId <= 0 || !mpaStorage.dbContainsMPA(mpaId))) {
            throw new ValidationException("MPA with id = " + mpaId + " not found");
        }
    }
}
