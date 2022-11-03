package ru.yandex.practicum.filmorate.storage.DAO;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPAStorage {

    List<MPA> getAllMPA();

    MPA getMPAById(int mpaId);
}
