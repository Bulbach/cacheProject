package by.alex.repository;

import by.alex.entity.Wagon;

import java.util.Optional;

public interface WagonRepository extends AbstractRepository<Wagon>{
    Optional<Wagon> findByWagonNumber(String wagonNumber);
}
