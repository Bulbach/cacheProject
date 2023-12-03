package by.alex;


import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import by.alex.mapper.WagonMapper;
import by.alex.service.WagonService;
import by.alex.util.print.PrintInfo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Runner runner = new Runner();
        WagonMapper wagonMapper = runner.getObject(WagonMapper.class);
        WagonService service = runner.getObject(WagonService.class);
        PrintInfo printInfo = new PrintInfo();

        UUID uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        WagonDto byId = service.getById(uuid);

        printInfo.print(byId);
        System.out.println("Wagon by ID " + byId);
        Collection<WagonDto> all = service.getAll();
        printInfo.printAll(all);

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

        Collection<WagonDto> changedAll = service.getAll();
        printInfo.printAll(changedAll);
        System.out.println("Before delete " + changedAll);

        service.delete(uuid);

        Collection<WagonDto> allAfterDel = service.getAll();
        System.out.println("After delete " + allAfterDel);
    }
}