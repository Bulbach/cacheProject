package by.alex.service;

import java.util.List;
import java.util.UUID;

public interface AbstractService<T> {
    T getById(UUID id);

    List<T> getAll();

    T create(T t);

    T update(T t);

    void delete(UUID id);
}
