package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.AlreadyExistsExeption;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public Film add(Film film) {
        if (filmStorage.findAllFilms().stream().map(Film::getName).anyMatch(x -> x.equals(film.getName()))) {
            throw new AlreadyExistsExeption("film with name " + film.getName() + " already exists");
        }
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        if (!filmStorage.getFilms().containsKey(film.getFilmId())) {
            throw new NotFoundException("film with id=" + film.getFilmId() + " not found");
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
        filmStorage.getFilmById(filmId).getLikes().add(userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);
        filmStorage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopular(Integer count) {
        return filmStorage.findAllFilms().stream()
                .sorted((p0, p1) -> Integer.compare(p1.getLikes().size(), p0.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private void validateFilmId(Integer filmId) {
        if (findAllFilms().stream().map(Film::getFilmId).noneMatch(x -> x.equals(filmId))) {
            throw new NotFoundException("film with id=" + filmId + " not found");
        }
    }
}
