package by.alex.mapper;

import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper()
public interface WagonMapper {

    WagonDto toDto(Wagon wagon);

    @Mapping(target = "id", ignore = true)
    Wagon toWagon(WagonDto wagonDto);
}
