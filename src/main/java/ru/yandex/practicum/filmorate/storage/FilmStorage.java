package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

@Component
public interface FilmStorage {

    Film add(Film film);

    Film update(Film film);

    List<Film> findAllFilms();

    Film getFilmById(Integer filmId);

    HashMap<Integer, Film> getFilms();
}
