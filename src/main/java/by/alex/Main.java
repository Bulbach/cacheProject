package by.alex;


import by.alex.entity.Wagon;
import by.alex.repository.impl.WagonRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Runner runner = new Runner();

        WagonRepository wagonRepository = new WagonRepository();

        UUID uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Optional<Wagon> wagon = wagonRepository.getById(uuid);
        System.out.println(wagon);
        Wagon wag = Wagon.builder()
                .id(UUID.fromString("b2596554-f405-4f4d-ade2-d7b462280321"))
                .wagonNumber("28813186")
                .loadCapacity(64)
                .yearOfConstruction(2001)
                .dateOfLastService(LocalDate.now())
                .build();

        Wagon wagon1 = wagonRepository.create(wag);
        System.out.println("Wagon was created "+ wagon1);

        Wagon wagUpdate = Wagon.builder()
                .id(UUID.fromString("b2596554-f405-4f4d-ade2-d7b462280321"))
                .wagonNumber("28813186")
                .loadCapacity(64)
                .yearOfConstruction(1999)
                .dateOfLastService(LocalDate.now())
                .build();
        Wagon wagon2 = wagonRepository.update(wagUpdate);
        System.out.println("Wagon was updated " + wagon2);

        Collection<Wagon> all = wagonRepository.getAll();
        System.out.println(all);

        wagonRepository.delete(uuid);

        Collection<Wagon> alldel = wagonRepository.getAll();
        System.out.println(alldel);
    }
}