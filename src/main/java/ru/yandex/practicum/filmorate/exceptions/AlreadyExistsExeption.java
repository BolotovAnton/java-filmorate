package ru.yandex.practicum.filmorate.exceptions;

public class AlreadyExistsExeption extends RuntimeException {
    public AlreadyExistsExeption(String message) {
        super(message);
    }
}
