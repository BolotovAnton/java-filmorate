package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    void updateFilm(Film film);

    List<Film> findAllFilms();

    Film getFilmById(Integer filmId);

    List<Film> getPopularFilms(Integer count, Integer genreId, Integer year);

    boolean dbContainsFilm(Integer filmId);

    void deleteFilmById(Integer filmId);
}
