package by.alex.dto;

public record InfoWagonDto(String wagonNumber,
                           int loadCapacity,
                           int yearOfConstruction,
                           String dateOfLastService) {
}
