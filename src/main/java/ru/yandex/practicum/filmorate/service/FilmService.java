package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.DAO.*;
import ru.yandex.practicum.filmorate.storage.DAO.review.ReviewLikesStorage;
import ru.yandex.practicum.filmorate.storage.DAO.review.ReviewStorage;
import ru.yandex.practicum.filmorate.validation.Validation;

import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final MPAStorage mpaStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;
    private final UserStorage userStorage;
    private final FilmGenresStorage filmGenresStorage;
    private final ReviewStorage reviewStorage;
    private final ReviewLikesStorage reviewLikesStorage;
    private final FeedStorage feedStorage;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                       MPAStorage mpaStorage,
                       LikesStorage likesStorage,
                       GenreStorage genreStorage,
                       UserStorage userStorage,
                       FilmGenresStorage filmGenresStorage,
                       ReviewStorage reviewStorage,
                       ReviewLikesStorage reviewLikesStorage, FeedStorage feedStorage) {
        this.filmStorage = filmStorage;
        this.mpaStorage = mpaStorage;
        this.likesStorage = likesStorage;
        this.genreStorage = genreStorage;
        this.userStorage = userStorage;
        this.filmGenresStorage = filmGenresStorage;
        this.reviewStorage = reviewStorage;
        this.reviewLikesStorage = reviewLikesStorage;
        this.feedStorage = feedStorage;
    }

    public Film add(Film film) throws ValidationException {
        Validation.validateFilm(film);
        Film filmWithId = filmStorage.addFilm(film);
        filmGenresStorage.addOrUpdateGenresForFilm(filmWithId);
        return getFilmById(filmWithId.getId());
    }

    public Film update(Film film) throws ValidationException {
        Validation.validateFilmId(filmStorage, film.getId());
        Validation.validateFilm(film);
        filmGenresStorage.addOrUpdateGenresForFilm(film);
        filmStorage.updateFilm(film);

        return getFilmById(film.getId());
    }

    public List<Film> findAllFilms() {
        List<Film> films = filmStorage.findAllFilms();
        for (Film film : films) {
            film.setGenres(genreStorage.getGenresSetForParticularFilm(film.getId()));
        }
        return films;
    }

    public Film getFilmById(Integer filmId) throws ValidationException {
        Validation.validateFilmId(filmStorage, filmId);
        Film film = filmStorage.getFilmById(filmId);
        film.setGenres(genreStorage.getGenresSetForParticularFilm(filmId));
        return film;
    }

    public void deleteFilmById(Integer filmId) throws ValidationException {
        Validation.validateFilmId(filmStorage, filmId);
        filmStorage.deleteFilmById(filmId);
    }

    public void addLike(Integer filmId, Integer userId) throws ValidationException {
        Validation.validateFilmId(filmStorage, filmId);
        Validation.validateUserId(userStorage, userId);
        likesStorage.addLike(filmId, userId);
        feedStorage.addFeed(userId, "LIKE", "ADD", filmId);
    }

    public void deleteLike(Integer filmId, Integer userId) throws ValidationException {
        Validation.validateFilmId(filmStorage, filmId);
        Validation.validateUserId(userStorage, userId);
        likesStorage.deleteLike(filmId, userId);
        feedStorage.addFeed(userId, "LIKE", "REMOVE", filmId);
    }

    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) throws ValidationException {
        Validation.validateCountOfLimit(count);
        Validation.validateGenreId(genreStorage, genreId);
        List<Film> films = filmStorage.getPopularFilms(count, genreId, year);
        for (Film film : films) {
            film.setGenres(genreStorage.getGenresSetForParticularFilm(film.getId()));
        }
        return films;
    }

    public List<MPA> getAllMPA() {
        return mpaStorage.getAllMPA();
    }

    public MPA getMPAById(int mpaId) throws ValidationException {
        Validation.validateMPAId(mpaStorage, mpaId);
        return mpaStorage.getMPAById(mpaId);
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenre();
    }

    public Genre getGenreById(int genreId) throws ValidationException {
        Validation.validateGenreId(genreStorage, genreId);
        return genreStorage.getGenreById(genreId);
    }

    public Review addReview(Review review) throws ValidationException {
        Validation.validateUserId(userStorage, review.getUserId());
        Validation.validateFilmId(filmStorage, review.getFilmId());
        Review reviewWithId = reviewStorage.addReview(review);
        feedStorage.addFeed(reviewWithId.getUserId(), "REVIEW", "ADD", reviewWithId.getReviewId());
        return getReviewById(reviewWithId.getReviewId());
    }

    public Review updateReview(Review review) throws ValidationException {
        Validation.validateReviewId(reviewStorage, review.getReviewId());
        review = reviewStorage.updateReview(review);
        feedStorage.addFeed(review.getUserId(), "REVIEW", "UPDATE", review.getReviewId());
        return review;
    }

    public void deleteReview(Integer reviewId) throws ValidationException {
        Validation.validateReviewId(reviewStorage, reviewId);
        int userId = getReviewById(reviewId).getUserId();
        reviewStorage.deleteReview(reviewId);
        feedStorage.addFeed(userId, "REVIEW", "REMOVE", reviewId);
    }

    public Review getReviewById(Integer reviewId) throws ValidationException {
        Validation.validateReviewId(reviewStorage, reviewId);
        return reviewStorage.getReviewById(reviewId);
    }

    public List<Review> getReviewsForFilm(Integer filmId, Integer count) throws ValidationException {
        Validation.validateCountOfLimit(count);
        if (filmId == null) {
            return reviewStorage.getAllReviewsWithLimit(count);
        } else {
            Validation.validateFilmId(filmStorage, filmId);
            return reviewStorage.getReviewsForFilm(filmId, count);
        }
    }

    public void addReviewLike(Integer reviewId, Integer userId, boolean isPositive) throws ValidationException {
        Validation.validateReviewId(reviewStorage, reviewId);
        Validation.validateUserId(userStorage, userId);
        reviewLikesStorage.addReviewLike(reviewId, userId, isPositive);
    }

    public void deleteReviewLike(Integer reviewId, Integer userId) throws ValidationException {
        Validation.validateReviewId(reviewStorage, reviewId);
        Validation.validateUserId(userStorage, userId);
        reviewLikesStorage.deleteReviewLike(reviewId, userId);
    }
}
