package by.alex.mapper;

import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface WagonMapper {
    WagonDto toDto(Wagon wagon);

    Wagon toWagon(WagonDto wagonDto);

    void updateModel(WagonDto wagonDto, @MappingTarget Wagon wagon);
}
