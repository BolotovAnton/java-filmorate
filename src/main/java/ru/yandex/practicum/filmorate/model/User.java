package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

    private int id;
    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private final String login;
    private String name;
    @NotBlank
    @Email
    private final String email;
    @NotNull
    @Past
    private final LocalDate birthday;
}
