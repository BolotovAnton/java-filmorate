package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.DAO.FilmStorage;
import ru.yandex.practicum.filmorate.storage.DAO.GenreStorage;
import ru.yandex.practicum.filmorate.storage.DAO.LikesStorage;
import ru.yandex.practicum.filmorate.storage.DAO.MPAStorage;

import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    private final MPAStorage mpaStorage;

    private final LikesStorage likesStorage;

    private final GenreStorage genreStorage;

    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage,
                       MPAStorage mpaStorage,
                       LikesStorage likesStorage,
                       GenreStorage genreStorage,
                       UserService userService) {
        this.filmStorage = filmStorage;
        this.mpaStorage = mpaStorage;
        this.likesStorage = likesStorage;
        this.genreStorage = genreStorage;
        this.userService = userService;
    }

    private void validateFilmId(Integer filmId) {
        if (findAllFilms().stream().map(Film::getId).noneMatch(x -> x.equals(filmId))) {
            throw new NotFoundException("film with id=" + filmId + " not found");
        }
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (filmStorage.findAllFilms().stream().map(Film::getId).noneMatch(x -> x.equals(film.getId()))) {
            throw new NotFoundException("film with id=" + film.getId() + " not found");
        }
        return filmStorage.update(film);
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        validateFilmId(filmId);
        return filmStorage.getFilmById(filmId);
    }

    public void addLike(Integer filmId, Integer userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);
        likesStorage.addLike(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);
        likesStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopular(Integer count) {
        return filmStorage.getPopular(count);
    }

    public List<MPA> getAllMPA() {
        return mpaStorage.getAllMPA();
    }

    public MPA getMPAById(int mpaId) {
        if (getAllMPA().stream().map(MPA::getId).noneMatch(x -> x.equals(mpaId))) {
            throw new NotFoundException("mpa with id=" + mpaId + " not found");
        }
        return mpaStorage.getMPAById(mpaId);
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenre();
    }

    public Genre getGenreById(int genreId) {
        if (genreStorage.getAllGenre().stream().map(Genre::getId).noneMatch(x -> x.equals(genreId))) {
            throw new NotFoundException("genre with id=" + genreId + " not found");
        }
        return genreStorage.getGenreById(genreId);
    }
}
