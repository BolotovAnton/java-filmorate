package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

@Component
@Qualifier("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {
    @Override
    public Film add(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public List<Film> findAllFilms() {
        return null;
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return null;
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        return null;
    }
}
