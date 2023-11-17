package by.alex.service.impl;

import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import by.alex.exceptions.CacheException;
import by.alex.mapper.WagonMapper;
import by.alex.repository.impl.WagonRepository;
import by.alex.service.AbstractService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WagonService implements AbstractService<WagonDto> {

    private WagonRepository wagonRepository;
    private WagonMapper wagonMapper;

    @Override
    public WagonDto getById(UUID id) {
        try {
            Optional<Wagon> optionalWagon = wagonRepository.getById(id);
            if (optionalWagon.isPresent()) {
                Wagon wagon = optionalWagon.get();
                return wagonMapper.toDto(wagon);
            } else {
                throw new CacheException("Wagon not found");
            }
        } catch (Exception e) {
            throw new CacheException("Error occurred while retrieving wagon" + e.getMessage());
        }
    }

    @Override
    public List<WagonDto> getAll() {
        return null;
    }

    @Override
    public WagonDto create(WagonDto wagonDto) {
        return null;
    }

    @Override
    public WagonDto update(WagonDto wagonDto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

}
