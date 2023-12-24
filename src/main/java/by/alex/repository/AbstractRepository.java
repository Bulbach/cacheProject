package by.alex.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AbstractRepository <T> {

    Collection<T> findAll();
    Collection<T> findAll(int page,int size);


   Optional<T> findById(UUID id) ;

    T create(T item) ;

    T update(T item) ;

    void delete(UUID id);

 }
