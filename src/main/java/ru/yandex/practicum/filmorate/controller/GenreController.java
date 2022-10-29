package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final FilmService filmService;

    @Autowired
    public GenreController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    List<Genre> getAllGenres() {
        log.debug("amount of genres {}", filmService.getAllGenres().size());
        return filmService.getAllGenres();
    }

    @GetMapping("/{genreId}")
    Genre getGenreById(@PathVariable int genreId) {
        log.debug("genre {} has been found", filmService.getGenreById(genreId));
        return filmService.getGenreById(genreId);
    }
}