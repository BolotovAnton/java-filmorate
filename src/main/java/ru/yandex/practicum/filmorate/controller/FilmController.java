package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;

    private void validation(Film film) throws ValidationException {
        if (film.getName().isBlank() || film.getName().isEmpty()) {
            ValidationException e = new ValidationException("empty film name");
            log.debug("Ошибка: {}", e.getMessage());
            throw e;
        }
        if (film.getDescription().length() > 200) {
            ValidationException e = new ValidationException("too long description");
            log.debug("Ошибка: {}", e.getMessage());
            throw e;
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            ValidationException e = new ValidationException("date of release should be after 28.12.1895");
            log.debug("Ошибка: {}", e.getMessage());
            throw e;
        }
//        if (film.getDuration() <= 0) {
//            ValidationException e = new ValidationException("duration should be positive");
//            log.debug("Ошибка: {}", e.getMessage());
//            throw e;
//        }
    }

    @PostMapping
    public Film add(@Valid @RequestBody Film film) throws ValidationException {
        validation(film);
        film.setId(idCounter);
        idCounter++;
        films.put(film.getId(), film);
        log.info("film has been added");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        validation(film);
        if (!films.containsKey(film.getId())) {
            ValidationException e = new ValidationException("films doesn't contain the film");
            log.debug("Ошибка: {}", e.getMessage());
            throw e;
        }
        films.put(film.getId(), film);
        log.info("film has been changed");
        return film;
    }

    @GetMapping
    public List<Film> findAllFilms() {
        log.info("Current number of films: {}", films.size());
        return new ArrayList<>(films.values());
    }
}
