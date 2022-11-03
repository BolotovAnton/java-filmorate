package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @Past
    private LocalDate birthday;

    public User(String name, String email, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("login", login);
        values.put("user_name", name);
        values.put("email", email);
        values.put("birthday", birthday);
        return values;
    }
}
