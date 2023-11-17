package by.alex.entity;

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
    private String wagonNumber;
    private int loadCapacity;
    private int yearOfConstruction;
    private LocalDate dateOfLastService;
}
