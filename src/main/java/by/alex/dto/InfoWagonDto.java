package by.alex.dto;

import java.time.LocalDate;

public record InfoWagonDto(String wagonNumber,
                           int loadCapacity,
                           int yearOfConstruction,
                           LocalDate dateOfLastService) {
}
