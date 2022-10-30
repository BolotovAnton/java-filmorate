package ru.yandex.practicum.filmorate.storage.inMemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DAO.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {

    private static int generateId = 1;

    private final HashMap<Integer, User> users = new HashMap<>();

    private static Integer getNextId(){
        return generateId++;
    }

    @Override
    public User add(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
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
    public List<User> getFriends(Integer userId) {
        //Логика для InMemory, если понядобится
        return null;
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        //Логика для InMemory, если понядобится
        return null;
    }

}
