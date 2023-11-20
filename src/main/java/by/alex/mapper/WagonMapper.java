package by.alex.mapper;

import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper()
public interface WagonMapper {
    WagonDto toDto(Wagon wagon);

    Wagon toWagon(WagonDto wagonDto);

    void updateModel(WagonDto wagonDto, @MappingTarget Wagon wagon);
}
