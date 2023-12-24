package by.alex.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class WagonDto {
  private   UUID id;
  private   String wagonNumber;
  private   int loadCapacity;
  private   int yearOfConstruction;
  private   String dateOfLastService;
}
