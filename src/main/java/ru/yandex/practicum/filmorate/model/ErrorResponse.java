package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String error;
    private String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }
}
