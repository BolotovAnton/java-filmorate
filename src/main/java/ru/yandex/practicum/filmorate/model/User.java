package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
//@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    private int id;
    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private final String login;
    private String name;
    @NotBlank
    @Email
    private final String email;
    @NonNull
    @Past
    private final LocalDate birthday;
    @JsonIgnore
    private Set<Integer> friendIds = new HashSet<>();
}
