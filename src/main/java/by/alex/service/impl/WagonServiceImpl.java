package by.alex.service.impl;

import by.alex.annotation.CustomCachableGet;
import by.alex.annotation.CustomCachebleCreate;
import by.alex.annotation.CustomCachebleDelete;
import by.alex.annotation.CustomCachebleUpdate;
import by.alex.dto.WagonDto;
import by.alex.entity.Wagon;
import by.alex.exceptions.CacheException;
import by.alex.mapper.WagonMapper;
import by.alex.repository.WagonRepository;
import by.alex.service.WagonService;
import by.alex.validation.ValidationEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WagonServiceImpl implements WagonService {

    private  WagonRepository wagonRepository;

    private  WagonMapper wagonMapper;
    @Autowired
    public WagonServiceImpl( WagonRepository wagonRepository, WagonMapper wagonMapper) {
        this.wagonRepository = wagonRepository;
        this.wagonMapper = wagonMapper;
    }

    @Override
    @CustomCachableGet
    public WagonDto getById(UUID id) {

        Optional<Wagon> optionalWagon = wagonRepository.findById(id);
        if (optionalWagon.isPresent()) {
            Wagon wagon = optionalWagon.get();
            WagonDto wagonDto = wagonMapper.toDto(wagon);

            return wagonDto;
        }

        throw new CacheException("Error with findById result is  ");
    }


    public List<WagonDto> getAll() {
        try {
            Collection<Wagon> wagons = wagonRepository.findAll();

            return wagons.stream()
                    .map(wagonMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CacheException("Error occurred while retrieving wagons" + e.getMessage());
        }
    }

    public List<WagonDto> getAll(int page, int pageSize) {
        try {
            Collection<Wagon> wagons = wagonRepository.findAll(page, pageSize);

            return wagons.stream()
                    .map(wagonMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CacheException("Error occurred while retrieving wagons" + e.getMessage());
        }
    }

    @Override
    @CustomCachebleCreate
    public WagonDto create(WagonDto wagonDto) {
        if (ValidationEntity.validate(wagonDto)) {

            try {
                Wagon wagon = wagonMapper.toWagon(wagonDto);
                Wagon createdWagon = wagonRepository.create(wagon);
                return wagonMapper.toDto(createdWagon);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create wagon", e);
            }
        } else {
            throw new CacheException("Entered not valid data" + wagonDto);
        }
    }

    @Override
    @CustomCachebleUpdate
    public WagonDto update(WagonDto wagonDto) {
        try {
            Wagon wagon = wagonMapper.toWagon(wagonDto);
            Wagon updatedWagon = wagonRepository.update(wagon);
            return wagonMapper.toDto(updatedWagon);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update wagon", e);
        }
    }

    @Override
    @CustomCachebleDelete
    public void delete(UUID id) {
        wagonRepository.delete(id);
    }

    public boolean isExist(String wagonNumber) {
        Optional<Wagon> existingWagon = wagonRepository.findByWagonNumber(wagonNumber);
        return existingWagon.isPresent();
    }
    public boolean isExist(UUID wagonUUID) {
        Optional<Wagon> existingWagon = wagonRepository.findById(wagonUUID);
        return existingWagon.isPresent();
    }
}
