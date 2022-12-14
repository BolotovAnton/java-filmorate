package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MPAController {

    private final FilmService filmService;

    @GetMapping
    public List<MPA> getAllMPA() {
        log.debug("amount of MPA {}", filmService.getAllMPA().size());
        return filmService.getAllMPA();
    }

    @GetMapping("/{mpaId}")
    public MPA getMPAById(@PathVariable int mpaId) throws ValidationException {
        MPA mpa = filmService.getMPAById(mpaId);
        log.debug("MPA with id = {} has been found", mpaId);
        return mpa;
    }
}
