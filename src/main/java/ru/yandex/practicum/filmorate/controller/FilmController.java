package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film add(@Valid @RequestBody Film film) throws ValidationException {
        film = filmService.add(film);
        log.debug("film {} has been added", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        film = filmService.update(film);
        log.debug("film {} has been updated", film.getName());
        return film;
    }

    @GetMapping
    public List<Film> findAllFilms() {
        log.debug("amount of films: {}", filmService.findAllFilms().size());
        return filmService.findAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable Integer filmId) throws ValidationException {
        Film film = filmService.getFilmById(filmId);
        log.debug("film {} has been found", film.getName());
        return film;
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilmById(@PathVariable Integer filmId) throws ValidationException {
        filmService.deleteFilmById(filmId);
        log.debug("film {} has been deleted", filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Integer filmId, @PathVariable Integer userId) throws ValidationException {
        filmService.addLike(filmId, userId);
        log.debug("like for film {} has been added", filmService.getFilmById(filmId).getName());
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Integer filmId, @PathVariable Integer userId) throws ValidationException {
        filmService.deleteLike(filmId, userId);
        log.debug("like for film {} has been deleted", filmService.getFilmById(filmId).getName());
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count,
                                      @RequestParam(required = false) Integer genreId,
                                      @RequestParam(required = false) Integer year) throws ValidationException {
        log.debug("amount of popular films is {}", filmService.getPopularFilms(count, genreId, year).size());
        return filmService.getPopularFilms(count, genreId, year);
    }
}
