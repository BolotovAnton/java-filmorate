//package ru.yandex.practicum.filmorate.storage.inMemory;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.storage.DAO.FilmStorage;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@Component
//@Qualifier("InMemoryFilmStorage")
//public class InMemoryFilmStorage implements FilmStorage {
//
//    private final HashMap<Integer, Film> films = new HashMap<>();
//
//    private static int generateId = 1;
//
//    private static Integer getNextId(){
//        return generateId++;
//    }
//
//    @Override
//    public Film add(Film film) {
//        film.setId(getNextId());
//        films.put(film.getId(), film);
//        return film;
//    }
//
//    @Override
//    public void update(Film film) {
//        films.put(film.getId(), film);
//    }
//
//    @Override
//    public List<Film> findAllFilms() {
//        return new ArrayList<>(films.values());
//    }
//
//    @Override
//    public Film getFilmById(Integer filmId) {
//        return films.get(filmId);
//    }
//
//    @Override
//    public List<Film> getPopular(Integer count) {
//        // Логика для InMemory, если понядобится
//        return null;
//    }
//}
