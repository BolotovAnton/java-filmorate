package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmGenresStorage {

    void addOrUpdateGenresForFilm(Film film);
}
