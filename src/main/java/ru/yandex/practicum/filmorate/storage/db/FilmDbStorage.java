package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.DAO.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Qualifier("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film addFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        int filmId = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        film.setId(filmId);
        return film;
    }

    @Override
    public void updateFilm(Film film) {
        String sql1 = "UPDATE FILMS SET FILM_NAME" + " = ?, DESCRIPTION = ?, " +
                "RELEASE_DATE = ?, DURATION = ?, MPA = ? " +
                "WHERE FILM_ID = ?";
        jdbcTemplate.update(sql1, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
    }

    @Override
    public List<Film> findAllFilms() {
        String sql = "SELECT F.*, M.MPA_NAME, COUNT(DISTINCT UL.USER_ID) AS RATE " +
                "FROM FILMS AS F " +
                "LEFT JOIN MPA AS M on M.MPA_ID = F.MPA " +
                "LEFT JOIN USER_LIKES AS UL on F.FILM_ID = UL.FILM_ID " +
                "GROUP BY F.FILM_ID ";
        return jdbcTemplate.query(sql, this::makeFilm);
    }

    @Override
    public Film getFilmById(Integer filmId) {
        String sql2 = "SELECT F.*, M.MPA_NAME, COUNT(DISTINCT UL.USER_ID) AS RATE " +
                "FROM FILMS AS F " +
                "LEFT JOIN MPA AS M on M.MPA_ID = F.MPA " +
                "LEFT JOIN USER_LIKES AS UL on F.FILM_ID = UL.FILM_ID " +
                "WHERE F.FILM_ID = ? " +
                "GROUP BY F.FILM_ID ";
        return jdbcTemplate.queryForObject(sql2, this::makeFilm, filmId);
    }

    @Override
    public void deleteFilmById(Integer filmId) {
        String sql = "DELETE FROM FILMS WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }

    @Override
    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        if (genreId != null && year != null) {
            String sql = "SELECT f.*, m.mpa_name, g.*, COUNT(l.USER_ID) as likes FROM films AS f " +
                    "LEFT JOIN mpa AS m ON f.mpa = m.mpa_id " +
                    "LEFT JOIN FILM_GENRES AS gf ON f.film_id = gf.film_id " +
                    "LEFT JOIN GENRES AS g ON gf.genre_id = g.genre_id " +
                    "LEFT JOIN USER_LIKES AS l on f.film_id = l.film_id " +
                    "WHERE g.genre_id = ? AND EXTRACT(YEAR FROM CAST(f.release_date AS date)) = ? " +
                    "GROUP BY f.film_id " +
                    "ORDER BY likes DESC " +
                    "LIMIT ?";
            return jdbcTemplate.query(sql, this::makeFilm, genreId, year, count);
        } else if (genreId != null) {
            String sql = "SELECT f.*, m.mpa_name, g.*, COUNT(l.USER_ID) as likes FROM films AS f " +
                    "LEFT JOIN mpa AS m ON f.mpa = m.mpa_id " +
                    "LEFT JOIN FILM_GENRES AS gf ON f.film_id = gf.film_id " +
                    "LEFT JOIN GENRES AS g ON gf.genre_id = g.genre_id " +
                    "LEFT JOIN USER_LIKES AS l on f.film_id = l.film_id " +
                    "WHERE g.genre_id = ? " +
                    "GROUP BY f.film_id " +
                    "ORDER BY likes DESC " +
                    "LIMIT ?";
            return jdbcTemplate.query(sql, this::makeFilm, genreId, count);
        } else if (year != null) {
            String sqlQuery = "SELECT f.*, m.mpa_name, COUNT(l.USER_ID) as likes FROM films AS f " +
                    "LEFT JOIN mpa AS m ON f.mpa = m.mpa_id " +
                    "LEFT JOIN USER_LIKES AS l on f.film_id = l.film_id " +
                    "WHERE EXTRACT(YEAR FROM CAST(f.release_date AS date)) = ? " +
                    "GROUP BY f.film_id " +
                    "ORDER BY likes DESC " +
                    "LIMIT ?";
            return jdbcTemplate.query(sqlQuery, this::makeFilm, year, count);
        }
        String sql = "SELECT f.*, m.mpa_name, COUNT(l.USER_ID) as likes FROM films AS f " +
                "LEFT JOIN mpa AS m ON f.mpa = m.mpa_id " +
                "LEFT JOIN USER_LIKES AS l on f.film_id = l.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY likes DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, this::makeFilm, count);
    }

    @Override
    public boolean dbContainsFilm(Integer filmId) {
        try {
            getFilmById(filmId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("film_name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int rate = rs.getInt("rate");
        MPA mpa = new MPA(rs.getInt("mpa"), rs.getString("mpa_name"));
        return new Film(id, name, description, releaseDate, duration, rate, mpa);
    }
}
