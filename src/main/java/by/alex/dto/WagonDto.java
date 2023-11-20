package by.alex.dto;

import java.time.LocalDate;
import java.util.UUID;

public record WagonDto(
        UUID id,
        String wagonNumber,
        int loadCapacity,
        int yearOfConstruction,
        LocalDate dateOfLastService) {
}
