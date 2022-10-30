package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film add(@Valid @RequestBody Film film) throws ValidationException {
        validationFilm(film);
        film = filmService.add(film);
        log.debug("film {} has been added", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        validationFilm(film);
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
    public Film getFilmById(@PathVariable Integer filmId) {
        Film film = filmService.getFilmById(filmId);
        log.debug("film {} has been found", film.getName());
        return film;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.addLike(filmId, userId);
        log.debug("like for film {} has been added", filmService.getFilmById(filmId).getName());
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.deleteLike(filmId, userId);
        log.debug("like for film {} has been deleted", filmService.getFilmById(filmId).getName());
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") Integer count) {
        if (count <= 0) {
            throw new IncorrectParameterException("value " + count + " of parameter count isn't valid");
        }
        log.debug("amount of popular films is {}, the most popular films is {}",
                filmService.getPopular(count).size(),
                filmService.getPopular(count).get(0).getName());
        return filmService.getPopular(count);
    }

    private void validationFilm(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("empty film name");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("too long description");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("date of release should be after 28.12.1895");
        }
    }
}
