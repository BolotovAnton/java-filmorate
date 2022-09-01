package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{

    private static int generateId = 1;

    private final HashMap<Integer, User> users = new HashMap<>();

    private static Integer getNextId(){
        return generateId++;
    }

    @Override
    public User add(User user) {
        user.setUserId(getNextId());
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Integer userId) {
        return users.get(userId);
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
