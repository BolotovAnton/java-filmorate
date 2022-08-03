package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {

    private int id;
    @NotBlank
    private final String login;
    private String name;
    @NotBlank
    @Email
    private final String email;
    @NotNull
    @Past
    private final LocalDate birthday;
}
