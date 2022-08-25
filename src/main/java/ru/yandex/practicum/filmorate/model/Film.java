package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Film {

    private int id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @NonNull
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
    @JsonIgnore
    private Set<Integer> likes = new HashSet<>();
}
