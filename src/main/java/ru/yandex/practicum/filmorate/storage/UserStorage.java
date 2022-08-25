package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

@Component
public interface UserStorage {

    User add(User user);

    User update(User user);

    List<User> findAllUsers();

    User getUserById(Integer userId);

    HashMap<Integer, User> getUsers();
}
