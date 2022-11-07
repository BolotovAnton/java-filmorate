package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {

    List<Genre> getAllGenre();

    Genre getGenreById(int genreId);

    Set<Genre> getGenresSetForParticularFilm(Integer filmId);

    boolean dbContainsGenre(Integer genreId);
}
