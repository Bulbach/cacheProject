package by.alex.dto;

import java.time.LocalDate;
import java.util.UUID;

public record WagonDto(
        String wagonNumber,
        int loadCapacity,
        int yearOfConstruction,
        LocalDate dateOfLastService) {
}
