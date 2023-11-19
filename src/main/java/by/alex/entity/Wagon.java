package by.alex.entity;

import by.alex.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Wagon {

    private UUID id;
    @Validation(regex = "^[a-zA-Z0-9]+$")
    private String wagonNumber;
    private int loadCapacity;
    private int yearOfConstruction;
    private LocalDate dateOfLastService;
}
