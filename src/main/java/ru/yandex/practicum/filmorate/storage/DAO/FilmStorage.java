package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film add(Film film);

    Film update(Film film);

    List<Film> findAllFilms();

    Film getFilmById(Integer filmId);

    List<Film> getPopular(Integer count);
}
