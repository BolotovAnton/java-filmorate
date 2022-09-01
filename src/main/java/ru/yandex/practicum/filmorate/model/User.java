package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private int userId;
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
    @JsonIgnore
    private HashMap<Integer, FriendStatus> friendIds = new HashMap<>();
}
