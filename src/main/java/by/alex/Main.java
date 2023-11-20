package by.alex;


import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import by.alex.mapper.WagonMapper;
import by.alex.service.WagonService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Runner runner = new Runner();
        WagonMapper wagonMapper = runner.getObject(WagonMapper.class);
        WagonService service = runner.getObject(WagonService.class);

        UUID uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Object byId = service.getById(uuid);
        System.out.println("Wagon by ID " + byId);


        Wagon wag = Wagon.builder()
                .wagonNumber("28813186")
                .loadCapacity(64)
                .yearOfConstruction(2001)
                .dateOfLastService(LocalDate.now())
                .build();
        WagonDto dto = wagonMapper.toDto(wag);
        WagonDto createdWagon = service.create(dto);
        System.out.println("Created wagon " + createdWagon);

        Wagon wagUpdate = Wagon.builder()
                .id(createdWagon.id())
                .wagonNumber("28813186")
                .loadCapacity(64)
                .yearOfConstruction(1999)
                .dateOfLastService(LocalDate.now())
                .build();
        WagonDto wagon2 = service.update(wagonMapper.toDto(wagUpdate));
        System.out.println("Wagon was updated " + wagon2);

        Collection<WagonDto> all = service.getAll();
        System.out.println("Before delete " + all);

        service.delete(uuid);

        Collection<WagonDto> allAfterDel = service.getAll();
        System.out.println("After delete " + allAfterDel);
    }
}